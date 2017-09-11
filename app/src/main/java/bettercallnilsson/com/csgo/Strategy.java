//
// CREATED BY SEBASTIAN NILSSON
//

package bettercallnilsson.com.csgo;

public class Strategy {

    private int _id;
    private String _title;
    private String _mapname;
    private String _team;
    private String _description;

    public Strategy(){

    }

    public Strategy(int id, String title, String mapname, String team, String description){
        this._id = id;
        this._title = title;
        this._mapname = mapname;
        this._team = team;
        this._description = description;

    }

    public Strategy(String title, String mapname, String team, String description){
        this._title = title;
        this._mapname = mapname;
        this._team = team;
        this._description = description;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setTitle(String title){
        this._title = title;
    }

    public String getTitle(){
        return this._title;
    }

    public void setMapname(String mapname){
        this._mapname = mapname;
    }

    public String getMapname(){
        return this._mapname;
    }

    public void setTeam(String team){
        this._team = team;
    }

    public String getTeam(){
        return this._team;
    }

    public void setDescription(String description){
        this._description = description;
    }

    public String getDescription(){
        return this._description;
    }

}
