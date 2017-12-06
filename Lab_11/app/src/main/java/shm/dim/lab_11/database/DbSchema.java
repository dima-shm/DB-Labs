package shm.dim.lab_11.database;

public class DbSchema {
    public static class FacultyTable {
        public static final String TABLE_NAME = "Facultys";
        public static class Columns {
            public static final String ID_FACULTY = "IdFaculty";
            public static final String FACULTY = "Faculty";
            public static final String DEAN = "Dean";
            public static final String OFFICE_TIME_TABLE = "OfficeTimeTable";
        }
    }

    public static class GroupTable {
        public static final String TABLE_NAME = "Groups";
        public static class Columns {
            public static final String ID_GROUP = "IdGroup";
            public static final String FACULTY = "Faculty";
            public static final String COURSE = "Course";
            public static final String NAME = "Name";
            public static final String HEAD = "Head";
            public static final String FK_CONSTRAINT = "Faculty_FK";
        }
    }

    public static class StudentTable {
        public static final String TABLE_NAME = "Students";
        public static class Columns {
            public static final String ID_STUDENT = "IdStudent";
            public static final String ID_GROUP = "IdGroup";
            public static final String NAME = "Name";
            public static final String BIRTHDATE = "Birthdate";
            public static final String ADDRESS = "Addres";
            public static final String FK_CONSTRAINT = "IdGroup_FK";
        }
    }

    public static class SubjectTable {
        public static final String TABLE_NAME = "Subjects";
        public static class Columns {
            public static final String ID_SUBJECT = "IdSubject";
            public static final String SUBJECT = "Subject";
        }
    }

    public static class ProgressTable {
        public static final String TABLE_NAME = "Progress";
        public static class Columns {
            public static final String ID_STUDENT = "IdStudent";
            public static final String ID_SUBJECT = "IdSubject";
            public static final String EXAMDATE = "ExamDate";
            public static final String MARK = "Mark";
            public static final String TEACHER = "Teacher";
            public static final String FK_STUDENT_CONSTRAINT = "IdStudent_FK";
            public static final String FK_SUBJECT_CONSTRAINT = "IdSubject_FK";
        }
    }
}