package dao;

import core.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* ToolDao is a child of AbstractDao<Direction>, this class help us find a tool in the database.
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class ToolDao extends AbstractDao<Tool> {

   /**
   * This method is used to get the instance of a specific tool given an id  
   * @param id is the id how the object we are looking for
   * @return Tool it returns a tool object with all his attributes.
   */
    @Override
    public Tool find(Integer id) {
        Tool tool = new Tool(id);
        try {
            ResultSet result = this.connect
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tools WHERE id = " + id
                    );
            if(result.first()) {
                tool.setName(result.getString("name"));
                tool.setLinked_light(result.getString("linked_light"));
                tool.setImage_url(result.getString("image_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tool;
    }
}
