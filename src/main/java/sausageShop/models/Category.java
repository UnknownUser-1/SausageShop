package sausageShop.models;


//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@XmlRootElement(name = "category")
public class Category implements Serializable, Comparable<Category> {

   // @XmlElement(name = "product")
    private ArrayList<Product> products;
    //@XmlAttribute
    private String title;
    private Integer id = null;

    public Category(String title) {
        this.products = new ArrayList<>();
        this.title = title;
    }

    public Category(){
        this.products = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return this.products.size();
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void deleteProduct(int i){
        this.products.remove(i);
    }

    public Product getProduct(int number){
        return this.products.get(number);
    }

    /**
     * Just for debug
     */


    @Override
    public int compareTo(Category o) {
        return (this.getSize()-o.getSize());
    }
}
