package dao;

import core.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class ToolDao extends AbstractDao<Tool> {

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
