package executor.accessors;

import storage.StorageManager;
import ui.UiCode;
import utils.AccessType;

public class AccessDeny extends Accessor {

    /**
     * Constructor for AccessDeny Class.
     */
    public AccessDeny() {
        super();
        this.accessType = AccessType.DENY;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr("Access Denied");
    }
}