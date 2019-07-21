package unipotsdam.gf.process.tasks.progress;

import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.tasks.Task;

public class UserTaskProgress extends TaskProgress {

    private User user;

    public UserTaskProgress(Task task, User user) {
        super(task);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
