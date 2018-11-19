package unipotsdam.gf.exceptions;

public class UserExistsInRocketChatException extends Exception {
    @Override
    public String getMessage() {
        return "Tried to create User but exists in RocketChat";
    }
}
