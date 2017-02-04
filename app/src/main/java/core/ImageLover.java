package core;

/**
* ImageLover is a contract that warranty the implementation of a certain behaviour on the concrete objects.
*
* @author  Frederic Bergeron
* @version 1.0
* @since   2017-02-04 
*/
public interface ImageLover {
    String getImage_url();
    String getName();
    void setImage_url(String url);
}
