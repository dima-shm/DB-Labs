package shm.dim.lab_10.database;

public final class DbSchema {
    public static class GroupsTable {
        public static final String TABLE_NAME = "Groups";
        public static class Columns {
            public static final String ID_GROUP = "IdGroup";
            public static final String FACULTY = "Faculty";
            public static final String COURSE = "Course";
            public static final String NAME = "Name";
            public static final String HEAD = "Head";
        }
    }

    public static class StudentsTable {
        public static final String TABLE_NAME = "Students";
        public static class Columns {
            public static final String ID_GROUP = "IdGroup";
            public static final String ID_STUDENT = "IdStudent";
            public static final String NAME = "Name";
            public static final String FK_CONSTRAINT = "idGroup_FK";
        }
    }
}