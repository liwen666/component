package com.temp.common.base.util.part1;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compress {

	private Logger logger = LoggerFactory.getLogger(Compress.class);

	private String rootDir = null;

	private String outFile = null;

	private Map<String, Boolean> excludesMap = new HashMap<>();

	private Map<File, LinkNode> fileNodes = new HashMap<>();

	private Map<String, File> fileMap = new HashMap<>();

	public static void main(String[] args) throws Exception {
		String rootDir = "E:\\app";
		String outFile = "E:\\bi.mini.js";
		List<String> excludes = new ArrayList<>();
		excludes.add("App.js");
		excludes.add("Application.js");
		excludes.add("cgt.js");
		excludes.add("ContextUtils.js");
		excludes.add("lic.js");
		excludes.add("Console.js");
		excludes.add("Overrides.js");
		new Compress(rootDir, outFile, excludes).mergeFiles();
	}

	public Compress(String rootDir, String outFile, List<String> excludes) {
		super();
		this.rootDir = rootDir;
		this.outFile = outFile;
		if (excludes != null) {
			for (String ex : excludes) {
				excludesMap.put(ex, true);
			}
		}
	}

	public void compressFiles() throws Exception {
		compressFiles(new File(rootDir));
	}

	public void clearCompressFiles() throws Exception {
		clearCompressFiles(new File(rootDir));
	}

	private void clearCompressFiles(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File sub : files) {
				clearCompressFiles(sub);
			}
		} else {
			String filePath = file.getAbsolutePath();
			if (filePath.endsWith(".js") && filePath.indexOf(".mini.") > 0) {
				file.deleteOnExit();
			}
		}
	}

	private void compressFiles(File file) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File sub : files) {
				compressFiles(sub);
			}
		} else {
			String filePath = file.getAbsolutePath();
			if (!excludesMap.containsKey(file.getName())) {
				if (filePath.endsWith(".js") && filePath.indexOf(".mini.") < 0) {
					filePath = filePath.substring(0, filePath.length() - 3) + ".mini.js";
					FileWriter out = new FileWriter(filePath);
					compressJS(file, out, -1, true, true, false, false);
				}
			}
		}
	}

	public void mergeFiles() throws Exception {
		compressFiles();
		List<File> list = new ArrayList<>();
		buildMergeList(new File(rootDir), list);
		for (File file : list) {
			fileMap.put(file.getAbsolutePath(), file);
			fileNodes.put(file, new LinkNode(file));
		}
		for (File file : list) {
			createLink(file);
		}

		// 会被其它文件依赖的
		List<LinkNode> toperNodes = new ArrayList<>();
		// 不被其它文件依赖的
		List<LinkNode> lowerNodes = new ArrayList<>();
		for (LinkNode node : fileNodes.values()) {
			boolean isLower = true;
			for (LinkNode sub : fileNodes.values()) {
				for (LinkNode pre : sub.getPres()) {
					if (pre.equals(node)) {
						node.getNext().add(sub);
						isLower = false;
					}
				}
			}
			if (isLower) {
				lowerNodes.add(node);
			}
			if (node.getNext().size() > 0) {
				toperNodes.add(node);
			}
		}

		List<LinkNode> tempNodes = new ArrayList<>();
		for (LinkNode linkNode : toperNodes) {
			tempNodes.add(linkNode);
		}

		for (int index = toperNodes.size() - 1; index >= 0; index--) {
			LinkNode node = toperNodes.get(index);
			for (LinkNode linkNode : tempNodes) {
				boolean flag = false;
				for (LinkNode next : linkNode.getNext()) {
					if (next.equals(node)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					toperNodes.remove(index);
					break;
				}
			}
		}

		Map<LinkNode, Boolean> hasPut = new HashMap<>();
		List<LinkNode> fList = new ArrayList<>();
		for (LinkNode linkNode : toperNodes) {
			createList(linkNode, fList, hasPut);
		}

		for (LinkNode linkNode : lowerNodes) {
			if (!hasPut.containsKey(linkNode)) {
				fList.add(linkNode);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(outFile);
		for (LinkNode linkNode : fList) {
			Long filelength = linkNode.getFile().length();
			byte[] filecontent = new byte[filelength.intValue()];
			FileInputStream in = new FileInputStream(linkNode.getFile());
			in.read(filecontent);
			in.close();
			fileOut.write(filecontent);
			fileOut.write("\r\n".getBytes());
		}
		fileOut.flush();
		fileOut.close();
		logger.info("文件总数：" + fileNodes.size());
		logger.info("最终列表：" + fList.size());
		clearCompressFiles();
	}

	private void createList(LinkNode node, List<LinkNode> list, Map<LinkNode, Boolean> hasPut) {
		if (!hasPut.containsKey(node)) {
			for (LinkNode linkNode : node.getPres()) {
				createList(linkNode, list, hasPut);
			}
			if (!hasPut.containsKey(node)) {
				list.add(node);
				hasPut.put(node, true);
			}
			for (LinkNode linkNode : node.getNext()) {
				createList(linkNode, list, hasPut);
			}
		}
	}

	private void createLink(File file) throws Exception {
		LinkNode node = fileNodes.get(file);
		List<File> refs = getRefs(file);
		for (File ref : refs) {
			node.getPres().add(fileNodes.get(ref));
		}
	}

	private void buildMergeList(File file, List<File> list) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File sub : files) {
				buildMergeList(sub, list);
			}
		} else {
			String filePath = file.getAbsolutePath();
			if (filePath.indexOf(".mini.") > 0) {
				list.add(file);
			}
		}
	}

	private List<File> getRefs(File file) throws Exception {
		List<File> refs = new ArrayList<>();
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = new FileInputStream(file);
		in.read(filecontent);
		in.close();
		String content = new String(filecontent, "UTF-8");
		getExtend(content, refs);
		getRequires(content, refs);
		getMixins(content, refs);
		return refs;
	}

	private void fillRefs(String s, List<File> refs) {
		s = s.replaceAll("\"", "");
		s = s.replaceAll("\\.", "/");
		if (s.startsWith("app/")) {
			s = s.substring(4);
		} else {
			return;
		}
		String filePath = rootDir + "/" + s + ".mini.js";
		File refFile = new File(filePath);
		if (refFile.exists() && fileMap.containsKey(refFile.getAbsolutePath())) {
			refs.add(fileMap.get(refFile.getAbsolutePath()));
		} else {
			logger.error("文件：" + filePath + "不存在");
		}
	}

	private void getRequires(String content, List<File> refs) {
		int fromIndex = 0;
		if ((fromIndex = content.indexOf("requires:[")) > 0) {
			int endIndex = content.indexOf("],", fromIndex);
			String requires = content.substring(fromIndex + 10, endIndex);
			if (requires == null || "".equals(requires.trim())) {
				return;
			}
			String[] fs = requires.split(",");
			for (String s : fs) {
				fillRefs(s, refs);
			}
		}
	}

	private void getExtend(String content, List<File> refs) {
		int fromIndex = 0;
		if ((fromIndex = content.indexOf("extend:")) > 0) {
			int endIndex = content.indexOf(",", fromIndex);
			String s = content.substring(fromIndex + 7, endIndex);
			fillRefs(s, refs);
		}
	}

	private void getMixins(String content, List<File> refs) {
		int fromIndex = 0;
		if ((fromIndex = content.indexOf("mixins:[")) > 0) {
			int endIndex = content.indexOf("],", fromIndex);
			String requires = content.substring(fromIndex + 8, endIndex);
			if (requires == null || "".equals(requires.trim())) {
				return;
			}
			String[] fs = requires.split(",");
			for (String s : fs) {
				fillRefs(s, refs);
			}
		}
	}

	public void compressJS(final File js, Writer out, int linebreakpos, boolean munge, boolean verbose,
			boolean preserveAllSemiColons, boolean disableOptimizations) throws IOException {
		InputStreamReader in = new InputStreamReader(new FileInputStream(js), "UTF-8");
		JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
			@Override
			public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
				if (logger.isInfoEnabled()) {
					logger.warn("\n[WARNING] in " + js.getAbsolutePath());
				}
				if (line < 0) {
					if (logger.isInfoEnabled()) {
						logger.warn("  " + message);
					}
				} else {
					if (logger.isInfoEnabled()) {
						logger.warn("  " + line + ':' + lineOffset + ':' + message);
					}
				}
			}

			@Override
			public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
				if (logger.isInfoEnabled()) {
					logger.error("[ERROR] in " + js.getAbsolutePath());
				}
				if (line < 0) {
					if (logger.isInfoEnabled()) {
						logger.error("  " + message);
					}
				} else {
					if (logger.isInfoEnabled()) {
						logger.error("  " + line + ':' + lineOffset + ':' + message);
					}
				}
			}

			@Override
			public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
					int lineOffset) {
				error(message, sourceName, line, lineSource, lineOffset);
				return new EvaluatorException(message);
			}
		});

		compressor.compress(out, linebreakpos, munge, verbose, preserveAllSemiColons, disableOptimizations);

		out.flush();
		out.close();
	}

	class LinkNode {

		private List<LinkNode> pres = new ArrayList<>();

		private List<LinkNode> next = new ArrayList<>();

		private File file = null;

		public LinkNode(File file) {
			super();
			this.file = file;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		/**
		 * 当前文件依赖的文件
		 * 
		 * @return
		 */
		public List<LinkNode> getPres() {
			return pres;
		}

		public void setPres(List<LinkNode> pres) {
			this.pres = pres;
		}

		/**
		 * 依赖当前文件的文件
		 * 
		 * @return
		 */
		public List<LinkNode> getNext() {
			return next;
		}

		public void setNext(List<LinkNode> next) {
			this.next = next;
		}
	}
}
