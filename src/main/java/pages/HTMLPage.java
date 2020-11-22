package pages;

public interface HTMLPage extends AutoCloseable {

    void open();
    boolean atPage();

}
