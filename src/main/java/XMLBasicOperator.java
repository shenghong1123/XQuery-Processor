/**
 * CSE 232B
 * Milestone1
 * Team: 14
 * Author: Shenghong Wang, Junyang Li
 * Data: 2/2/2017
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashSet;


public class XMLBasicOperator {

    public static Document output; // Global Document object used to output query result;

    /**
     * Read file
     */
    public static Document DOMParser(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            return doc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create a xml file storing the query result.
     */
    public static void XMLBuilder() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            output = document;
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    /**
     * Put the result into output file.
     */
    public static void  writeFile(MyNodeList result) {
        String xmlFilePath = "/Users/WSH/Desktop/output.xml";
        try {
            //root element
            Element root = output.createElement("FinalResult");
            output.appendChild(root);

            //append result
            if(result != null) {
                for(int i = 0; i < result.getLength(); i++) {
                    Node n = output.importNode(result.item(i), true);
                    root.appendChild(n);
                }
            }

            //create the xml file
            //transform the DOM object to an xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(output);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);
        }
        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * Get the root node of XML document
     */
    public static MyNodeList getRootElement(Document doc) {
        Node root = doc.getDocumentElement();
        MyNodeList curNodeList = new MyNodeList();
        curNodeList.add(root);
        return curNodeList;
    }

    /**
     * Get unique<l1, l2>
     */
    public static MyNodeList getUnique(MyNodeList l1, MyNodeList l2) {
        if(l1.getLength() == 0 && l2.getLength() == 0) return new MyNodeList();
        if(l1.getLength() == 0 && l2.getLength() != 0) return deleteDuplicate(l2);
        if(l1.getLength() != 0 && l2.getLength() == 0) return deleteDuplicate(l1);
        MyNodeList result = new MyNodeList();
        HashSet<Node> set = new HashSet<Node>();
        for(int i = 0; i < l1.getLength(); i++) {
            if(!set.contains(l1.item(i))) {
                set.add(l1.item(i));
                result.add(l1.item(i));
            }
        }
        for(int i = 0; i < l2.getLength(); i++) {
            if(!set.contains(l2.item(i))) {
                set.add(l2.item(i));
                result.add(l2.item(i));
            }
        }
        return result;
    }


    /**
     * Delete the repeat node in list
     */
    public static MyNodeList deleteDuplicate(MyNodeList curNodeList) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        MyNodeList result = new MyNodeList();
        HashSet<Node> set = new HashSet<Node>();
        for(int i = 0; i < curNodeList.getLength(); i++) {
            if(!set.contains(curNodeList.item(i))) {
                set.add(curNodeList.item(i));
                result.add(curNodeList.item(i));
            }
        }
        return result;
    }

    /**
     * Get the nodes with specific attribute name
     */
    public static MyNodeList hasAttName(MyNodeList curNodeList, String attName) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        MyNodeList result = new MyNodeList();
        for(int i = 0; i < curNodeList.getLength(); i++) {
            Node cur = curNodeList.item(i);
            if(cur.getNodeType() == Node.ELEMENT_NODE && ((Element)cur).hasAttribute(attName)) result.add(cur);
        }
        return result;
    }

    /**
     * Get all children of current node list
     */
    public static MyNodeList getAllChildren(MyNodeList curNodeList) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        MyNodeList children = new MyNodeList();
        for (int i = 0; i < curNodeList.getLength(); i++) {
            Node n = curNodeList.item(i);
            NodeList childrenList = n.getChildNodes();
            for (int j = 0; j < childrenList.getLength(); j++) {
                Node child = childrenList.item(j);
                children.add(child);
            }
        }

        return children;
    }

    /**
     * Get the nodes with specific tag name
     */
    public static MyNodeList getChildrenByTag(MyNodeList curNodeList, String tagName) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        MyNodeList result = new MyNodeList();
        MyNodeList children = getAllChildren(curNodeList);
        for(int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            if(n.getNodeName().equals(tagName)) result.add(n);
        }
        return result;
    }

    /**
     * Get the text nodes of current node list
     */
    public static MyNodeList getText(MyNodeList curNodeList) {
        if(curNodeList == null || curNodeList.getLength() == 0) return new MyNodeList();
        MyNodeList textList = new MyNodeList();
        for(int i = 0; i < curNodeList.getLength(); i++) {
            Node n = curNodeList.item(i);
            NodeList children = n.getChildNodes();
            for(int j = 0; j < children.getLength(); j++) {
                if(children.item(j).getNodeType()== Node.TEXT_NODE) {
                    textList.add(children.item(j));
                }
            }
        }

        return textList;
    }

    /**
     * Get all parents of current node list
     */
    public static MyNodeList getAllParents(MyNodeList curNodeList) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        //System.out.println("Trying to get all parent...");
        MyNodeList parents = new MyNodeList();
        for(int i = 0; i < curNodeList.getLength(); i++) {
            Node c = curNodeList.get(i);
            Node p = c.getParentNode();
            parents.add(p);
        }
        //System.out.println("Already get all parent");
        return parents;
    }

    /**
     * Concat two node lists (repeat node is acceptable)
     */
    public static MyNodeList concat(MyNodeList l1, MyNodeList l2) {
        if(l1.getLength() == 0 && l2.getLength() == 0) return new MyNodeList();
        if(l1.getLength() == 0 && l2.getLength() != 0) return l2;
        if(l1.getLength() != 0 && l2.getLength() == 0) return l1;
        MyNodeList concatenation = new MyNodeList();
        for(int i = 0; i < l1.getLength(); i++) concatenation.add(l1.item(i));
        for(int j = 0; j < l2.getLength(); j++) concatenation.add(l2.item(j));
        return concatenation;
    }

    /**
     * Get all descendants of current node list
     */
    public static MyNodeList getAllDescendant(MyNodeList curNodeList) {
        if(curNodeList.getLength() == 0) return new MyNodeList();
        //System.out.println("Trying to get all descendants...");
        MyNodeList result = new MyNodeList();
        getAllDescendantHelper(curNodeList, result);
        //System.out.println("Already get all descendants");
        return result;
    }
    public static MyNodeList getAllDescendantHelper(MyNodeList curNodeList, MyNodeList result) {
        if(curNodeList.size() == 0) return result;
        MyNodeList children = getAllChildren(curNodeList);
        for(int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            result.add(child);
        }
        return getAllDescendantHelper(children, result);
    }

    /**
     * Check if there exist value-equal nodes in given NodeLists
     */
    public static boolean existValueEqual(MyNodeList l1, MyNodeList l2) {
        if(l1 == null || l2 == null ||l1.getLength() == 0 || l2.getLength() == 0) return false;
        for(int i = 0; i < l1.getLength(); i++) {
            for (int j = 0; j < l2.getLength(); j++) {
                Node n1 = l1.item(i);
                Node n2 = l2.item(j);
                if(n1.isEqualNode(n2)) return true;
            }
        }
        return false;
    }

    /**
     * Check if there exist id-equal nodes in given NodeLists
     */
    public static boolean existIdEqual(MyNodeList l1, MyNodeList l2) {
        if(l2 == null || l1 == null || l1.getLength() == 0 || l2.getLength() == 0) return false;
        for(int i = 0; i < l1.getLength(); i++) {
            for (int j = 0; j < l2.getLength(); j++) {
                Node n1 = l1.item(i);
                Node n2 = l2.item(j);
                if(n1.isSameNode(n2)) return true;
            }
        }
        return false;
    }

    /**
     * Make a element whose has "tagName" and children "list"
     */
    public static MyNodeList makeElement(MyNodeList list, String tagName) {
        try{
                Element parent = output.createElement(tagName);
                for(Node n: list) {
                    Node copy = output.importNode(n, true);
                    parent.appendChild(copy);
                }
                return new MyNodeList(parent);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



