package com.temp.common.complier.tools;

import groovy.lang.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/3/11  17:51
 */
public class JavaParseObject {
    private String source;
    private String packageName;
    private List<String> importReference;
    private String className;
    private String Content;
    private Map<ElementEnum, List<Tuple2<String, String>>> element;

    public JavaParseObject() {
        element = new HashMap<>();
        element.put(ElementEnum.PACKAGE, new ArrayList<>());
        element.put(ElementEnum.IMPORT, new ArrayList<>());
        element.put(ElementEnum.ENUM, new ArrayList<>());
        element.put(ElementEnum.CLASS, new ArrayList<>());
    }

    public List<String> getImportRefrence() {
        return importReference;
    }

    public void setImportRefrence(List<String> importRefrence) {
        this.importReference = importReference;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public JavaParseObject build(String content) {
        char[] chars = content.toCharArray();
        // \r 表示13 \n 表示10
        StringBuffer javaSource = new StringBuffer();
        StringBuffer line = new StringBuffer();
        boolean isSimpleSign = true;
        boolean contentStart = false;
        for (int i = 0; i < chars.length; i++) {
            line.append(chars[i]);
            if (chars[i] == 10) {
                String trim = line.toString().trim();
                if ("".equals(trim)) {
                    continue;
                }
                if (trim.length() > 1 && trim.charAt(0) == 47 && trim.charAt(1) == 47) {
                    line.setLength(0);
                } else if (trim.length() > 1 && trim.charAt(0) == 47 && trim.charAt(1) == 42) {
                    isSimpleSign = false;
                    line.setLength(0);
                } else {
                    if (line.toString().startsWith(ElementEnum.PACKAGE.getType())) {
                        element.get(ElementEnum.PACKAGE).add(new Tuple2<>(ElementEnum.PACKAGE.getType(), line.toString()));
                        packageName = line.toString();
                    }
                    if (line.toString().startsWith(ElementEnum.IMPORT.getType())) {
                        element.get(ElementEnum.IMPORT).add(new Tuple2<>(ElementEnum.IMPORT.getType(), line.toString()));
                    }
                    if (line.toString().contains(ElementEnum.CLASS.getType())) {
                        className = line.substring(line.indexOf(ElementEnum.CLASS.getType()) + 7, line.indexOf(Token.START_OBJECT.getToken())).trim();
                        contentStart = true;
                    }
                    if (isSimpleSign && contentStart) {
                        javaSource.append(line);
                    }
                    if (trim.length() > 1 && trim.charAt(trim.length() - 1) == 47 && trim.charAt(trim.length() - 2) == 42) {
                        isSimpleSign = true;
                    }
                    line.setLength(0);
                }
            }
        }
        element.get(ElementEnum.CLASS).add(new Tuple2<>(ElementEnum.CLASS.getType(), javaSource.toString()));
        this.source = javaSource.toString();
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String changePackage(String pack, String clazzName) {
        StringBuffer source = new StringBuffer();
        source.append(ElementEnum.PACKAGE.getType()).append(pack + "\n");
        this.element.get(ElementEnum.IMPORT).stream().forEach(e -> source.append(e.getV2()));
        source.append(this.getSource().replace(this.getClassName(), clazzName));
        return source.toString();
    }

    public String getSource() {
        return source;
    }
}
