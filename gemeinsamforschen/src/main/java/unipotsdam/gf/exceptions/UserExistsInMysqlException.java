package unipotsdam.gf.exceptions;

public class UserExistsInMysqlException extends Exception {

    @Override
    public String getMessage() {
        return "Tried to create User but exists in mysql";
    }
}
