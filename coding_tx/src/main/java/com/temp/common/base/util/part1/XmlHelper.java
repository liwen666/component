package com.temp.common.base.util.part1;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlHelper {

	private Document document;

	/**
	 * 解析包含命名空间的XML
	 * 
	 * @param in
	 * @param map
	 */
	public XmlHelper(InputStream in) {
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * 根据xpath查询该节点下的文本值
	 * 
	 * @param xmlpath
	 *            xpath路径表达式
	 * @return 返回查找到的文本值
	 */
	public String getNodeValue(String xmlpath) {
		String value = document.selectSingleNode(xmlpath).getText();
		return value;
	}

	/**
	 * 根据xpath设置相应节点的值
	 * 
	 * @param xpath
	 *            xpath路径表达式
	 * @param value
	 *            所要设置的值
	 * @return
	 */
	public boolean setNodeValue(String xpath, String value) {
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			node.setText(value);
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 根据xpath表达式，返回所有符合条件的节点
	 * 
	 * @param xpath
	 *            xpath表达式
	 * @return
	 */
	public List<Node> selectNodeList(String xpath) {
		return document.selectNodes(xpath);
	}

	/**
	 * 判断一个节点的属性值是否为指定值
	 * 
	 * @param xn
	 *            节点
	 * @param attribute
	 *            节点属性名称
	 * @param value
	 *            节点属性值
	 * @return
	 */
	public boolean judgeNode(Node xn, String attribute, String value) {
		Attribute attrElement = ((Element) xn).attribute(attribute);
		if (attrElement == null) {
			return false;
		} else {
			if (attrElement.getText().equals(value)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 根据xpath获取节点相应属性的值
	 * 
	 * @param xpath
	 *            xpath路径表达式
	 * @param attribute
	 *            属性名称
	 * @return
	 */
	public String getAttributeValue(String xpath, String attribute) {
		Node xn = document.selectSingleNode(xpath);
		if (xn != null) {
			Element element = (Element) xn;
			return element.attribute(attribute).getStringValue();
		} else {
			return null;
		}

	}

	/**
	 * 设置一个节点的属性值
	 * 
	 * @param xpath
	 *            节点xpath路径
	 * @param attribute
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 */
	public boolean setAttributeValue(String xpath, String attribute, String value) {
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			Element element = (Element) node;
			Attribute attr = element.attribute(attribute);
			if (attr != null) {
				attr.setValue(value);
				return true;
			} else {
				element.add(new DefaultAttribute(attribute, value));
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断是否存在跟指定xpath相应的结点
	 * 
	 * @param xpath
	 *            xpath路径表达式
	 * @return
	 */
	public boolean checkExist(String xpath) {
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询指定的节点
	 * 
	 * @param xpath
	 *            xpath路径表达式
	 * @return
	 * @throws Exception
	 */
	public Node selectNode(String xpath) {
		Node xn = document.selectSingleNode(xpath);
		return xn;
	}

	/**
	 * 插入新的节点
	 * 
	 * @param parentXpath
	 *            新插入节点的父节点的xpath表达式
	 * @param nodeName
	 *            新插入节点的名称
	 * @param attriValues
	 *            新插入节点的属性及属性值
	 * @param nodeValues
	 *            新插入节点的值
	 * @return
	 */
	public boolean insertNode(String parentXpath, String nodeName, Map<String, String> attriValues, String nodeValue) {
		Node objRootNode = selectNode(parentXpath);
		if (objRootNode == null) {
			return false;
		}
		Element element = ((Element) objRootNode).addElement(nodeName);
		if (attriValues != null) {
			for (String key : attriValues.keySet()) {
				element.addAttribute(key, attriValues.get(key));
			}
		}
		if (nodeValue != null) {
			element.setText(nodeValue);
		}
		return true;
	}

	/**
	 * 插入新的节点
	 * 
	 * @param parentXpath
	 *            新插入节点的父节点的xpath表达式
	 * @param nodeName
	 *            新插入节点的名称
	 * @param attriValues
	 *            新插入节点的属性及属性值
	 * @return
	 */
	public boolean insertNode(String parentXpath, String nodeName, Map<String, String> attriValues) {
		return insertNode(parentXpath, nodeName, attriValues, null);
	}

	/**
	 * 删除一个节点
	 * 
	 * @param xn
	 *            所要删除的节点
	 * @return
	 */
	public boolean deleteNode(Node xn) {
		Node node = xn.getParent();
		if (node != null) {
			document.remove(node);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 删除一个节点
	 * 
	 * @param xpath
	 *            要删除节点的xpath表达式
	 * @return
	 */
	public boolean deleteNode(String xpath) {
		Node node = selectNode(xpath);
		if (node != null) {
			deleteNode(node);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据xpath表达式，删除一系列的节点
	 * 
	 * @param xpath
	 *            xpath表达式
	 * @return
	 */
	public boolean deleteNodeList(String xpath) {
		List<Node> nodeList = selectNodeList(xpath);
		if (nodeList != null) {
			for (Node node : nodeList) {
				deleteNode(node);
			}
		}
		return true;
	}

	/**
	 * 更新节点
	 * 
	 * @param xpath
	 *            节点路径
	 * @param attriValues
	 *            节点的属性名称及属性值
	 * @return
	 */
	public boolean updateNode(String xpath, Map<String, String> attriValues) {
		return updateNode(xpath, attriValues, null);
	}

	/**
	 * 更新节点
	 * 
	 * @param xpath
	 *            节点xpath路径表达式
	 * @param attriValues
	 *            节点的属性名称及属性值
	 * @param nodeValues
	 *            节点的值
	 * @return
	 */
	public boolean updateNode(String xpath, Map<String, String> attriValues, String nodeValue) {
		Node xn = selectNode(xpath);
		if (xn == null) {
			return false;
		}
		if (attriValues != null) {
			for (String key : attriValues.keySet()) {
				Attribute attribute = ((Element) xn).attribute(key);
				if (attribute != null) {
					attribute.setText(attriValues.get(key));
				}
			}
		}
		if (nodeValue != null) {
			xn.setText(nodeValue);
		}
		return true;
	}

	/**
	 * 根据父结点的xpath获取其所有的子节点
	 * 
	 * @param parentPath
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Node> getChildNodes(String parentxPath) {
		Node node = selectNode(parentxPath);
		if (node == null) {
			return null;
		}
		Element element = (Element) node;
		List<Node> nodes = new ArrayList<Node>();
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element e = (Element) i.next();
			if (e instanceof Node) {
				nodes.add(e);
			}
		}
		return nodes;
	}

	public Node getRootNode() {
		return document.getRootElement();
	}

	public boolean saveDocument(OutputStream output) throws IOException {
		if (this.document != null) {
			output.write(this.document.asXML().getBytes());
			output.flush();
		}
		return false;
	}
}
