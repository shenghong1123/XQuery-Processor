/**
 * Created by junya on 2/14/2017.
 */


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;


public class MyNodeList extends LinkedList<Node>  implements NodeList {

    public MyNodeList() {}

    public MyNodeList(Node singleNode) {
        this.add(singleNode);
    }

    public int getLength() {
        return size();
    }

    public Node item(int index) { return get(index);}

    public int hashCode() {
        int hashcode = 0;
        for(Node n: this) {

            hashcode += Objects.hash(n.getTextContent());
        }
        return hashcode;
    }
    public boolean equals(Object o) {
        if(!(o instanceof MyNodeList)) return false;
        if(o == this) return true;
        if(((MyNodeList) o).getLength() != this.getLength()) return false;
        for(int i = 0; i < this.getLength(); i++) {

            if (this.item(i).getTextContent() != ((MyNodeList) o).item(i).getTextContent()) return false;
        }
        return true;
    }



}