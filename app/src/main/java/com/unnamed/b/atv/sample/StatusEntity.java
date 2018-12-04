package com.unnamed.b.atv.sample;

import com.unnamed.b.atv.sample.entity.Entity;

import java.util.ArrayList;
import java.util.ListIterator;

class StatusEntity extends Entity {
    public static final int STATUS_ID_APPROVED = 1;
    public static final int STATUS_ID_SCHEDULED = 12;
    public static final int STATUS_ID_STARTED = 13;
    public static final int STATUS_ID_COMPLETED = 14;
    public static final int STATUS_ID_PENDING = 15;
    public static final int STATUS_ID_CLOSED = 16;
    public static final int STATUS_ID_SUSPENDED = 17;
    public static final int STATUS_ID_DENIED = 18;
    public static final int STATUS_ID_DELETED = 19;
    public static final int STATUS_ID_MY_WORKORDER = 29;

    private int statusId;
    private String statusName;

    public StatusEntity(int id, String name) {
        setStatusName(name);
        setStatusId(id);
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public long getKey() {
        return statusId;
    }

    @Override
    public String getValue() {
        return statusName;
    }

    @Override
    public void setValue(String s) {
        super.setValue(s);
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * Added By : Dhanashree C
     * Sprint 16 | ACMMS-98 | Work Order queue - Filter Functionality
     * <p>
     * Add status names
     *
     * @return entity list of selected filters
     */
    public static ArrayList<Entity> getStatusArray() {
        ArrayList<Entity> entityArrayList = new ArrayList<>();
        entityArrayList.add(new StatusEntity(STATUS_ID_APPROVED, getStatusName(STATUS_ID_APPROVED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_SCHEDULED, getStatusName(STATUS_ID_SCHEDULED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_STARTED, getStatusName(STATUS_ID_STARTED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_COMPLETED, getStatusName(STATUS_ID_COMPLETED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_PENDING, getStatusName(STATUS_ID_PENDING)));
        entityArrayList.add(new StatusEntity(STATUS_ID_CLOSED, getStatusName(STATUS_ID_CLOSED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_SUSPENDED, getStatusName(STATUS_ID_SUSPENDED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_DENIED, getStatusName(STATUS_ID_DENIED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_DELETED, getStatusName(STATUS_ID_DELETED)));
        entityArrayList.add(new StatusEntity(STATUS_ID_MY_WORKORDER, getStatusName(STATUS_ID_MY_WORKORDER)));
        return entityArrayList;
    }

    public static String getStatusName(int statusId) {
        switch (statusId) {
            case STATUS_ID_APPROVED:
                return "Approved";

            case STATUS_ID_SCHEDULED:
                return "Scheduled";

            case STATUS_ID_STARTED:
                return "Started";

            case STATUS_ID_COMPLETED:
                return "Completed";

            case STATUS_ID_PENDING:
                return "Pending";

            case STATUS_ID_CLOSED:
                return "Closed";

            case STATUS_ID_SUSPENDED:
                return "Suspended";

            case STATUS_ID_DENIED:
                return "Denied";

            case STATUS_ID_DELETED:
                return "Deleted";

            case STATUS_ID_MY_WORKORDER:
                return "My Work Order";

            default:
                return "";
        }
    }

    /**
     * Added By : Dhanashree C
     * Sprint 16 | ACMMS-98 | Work Order queue - Filter Functionality
     * <p>
     * Get default selected status names
     *
     * @return entity list of selected filters
     */
    public static ArrayList<Integer> getDefaultStatusIDs() {
        ArrayList<Integer> entityArrayList = new ArrayList<>();
        entityArrayList.add(STATUS_ID_APPROVED);
        entityArrayList.add(STATUS_ID_SCHEDULED);
        entityArrayList.add(STATUS_ID_STARTED);
        entityArrayList.add(STATUS_ID_COMPLETED);
        return entityArrayList;
    }

    public static ArrayList<Entity> getNewWoDefaultStatus() {
        ArrayList<Entity> entities = getStatusArray();
        ListIterator<Entity> iterator = entities.listIterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (!(entity.getKey() == STATUS_ID_APPROVED || entity.getKey() == STATUS_ID_SUSPENDED || entity.getKey() == STATUS_ID_DENIED)) {
                iterator.remove();
            }
        }
        return entities;
    }
}
