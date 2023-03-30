package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tVSignUp;


//    public static final String DATABASE_NAME = "quizapp.db";
    public static final String DATABASE_NAME = "quiz.db";
    SQLiteDatabase db;
    EditText edtUsername, edtPassword;
    Button btnClose;

    //column table question
    public static final String TABLE_QUESTION = "tblquestion";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_SUBJECT_ID_RF = "subject_id";
    public static final String COLUMN_ANSWER_NR = "answer_cr";
    public static final String COLUMN_USER_SELECTED = "userSelectedAnswer";
    //end question

    //table subject
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String COLUMN_SUBJECT_ID = "subject_id";
    private static final String COLUMN_SUBJECT_NAME = "subject_name";
//    private void initDB() {
//        db = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
//        String sql;
//        try {
//            if(!isTableExists(db,"tbluser")) {
//                sql = "Create table tbluser (id_user Integer not null primary key AUTOINCREMENT,";
//                sql += "username Text not null,";
//                sql += "password Text not null)";
//                db.execSQL(sql);
//                sql = "insert into tbluser(username,password) values ('admin','admin')";
//                db.execSQL(sql);
//            }
//            if(!isTableExists(db,TABLE_QUESTION)) {
//                sql = "CREATE TABLE "+TABLE_QUESTION + "("
//                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                        + COLUMN_QUESTION + " TEXT,"
//                        + COLUMN_OPTION1 + " TEXT,"
//                        + COLUMN_OPTION2 + " TEXT,"
//                        + COLUMN_OPTION3 + " TEXT,"
//                        + COLUMN_OPTION4 + " TEXT,"
//                        + COLUMN_SUBJECT_ID_RF + " INTEGER,"
//                        + COLUMN_ANSWER_NR + " TEXT,"
//                        + COLUMN_USER_SELECTED + " TEXT"
//                        + ")";
//                db.execSQL(sql);
//            }
//            if(!isTableExists(db,TABLE_SUBJECTS)) {
//                sql = "CREATE TABLE " + TABLE_SUBJECTS + "("
//                        + COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY," + COLUMN_SUBJECT_NAME + " TEXT)";
//                db.execSQL(sql);
//            }
//        }
//        catch (Exception ex) {
//            Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void addQuestions()
//    {
//        Question q1=new Question("nam nay nam con gi","ty", "suu ", "dan", "meo",1,"ty");
//        this.addQuestion(q1);
//        Question q2=new Question("What your name","duy", "duy1 ", "duy2", "duy3",1,"duy");
//        this.addQuestion(q2);
//        Question q3=new Question("Nam nay nam bao nhieu","2022", "2023 ", "2024", "2025",1,"2023");
//        this.addQuestion(q2);
//
//
//    }

//    public void addQuestion(Question quest) {
//
//        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_QUESTION, quest.getQuestion());
//        values.put(COLUMN_OPTION1, quest.getOption1());
//        values.put(COLUMN_OPTION2, quest.getOption2());
//        values.put(COLUMN_OPTION3, quest.getOption3());
//        values.put(COLUMN_OPTION4, quest.getOption4());
//        values.put(COLUMN_SUBJECT_ID_RF, quest.getSubject_id());
//        values.put(COLUMN_ANSWER_NR, quest.getAnswer_cr());
//        // Inserting Row
//        db.insert(TABLE_QUESTION, null, values);
//
//
//    }



//    private boolean isTableExists(SQLiteDatabase database, String tableName) {
//        Cursor cursor = database.rawQuery("select distinct tbl_name from sqlite_master where tbl_name" +
//                "= '" + tableName + "'", null);
//        if(cursor != null) {
//            if(cursor.getCount() > 0) {
//                cursor.close();
//                return true;
//
//            }
//
//            cursor.close();
//        }
//        return false;
//    }
    private boolean isUser(String username, String password) {
        try {
            db = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
            Cursor c = db.rawQuery("Select * from tbluser where username=? and password = ?",
                    new String[] {username, password});
            c.moveToFirst();
            if(c.getCount() > 0) {
                return true;
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = Database.initDatabase(this,DATABASE_NAME);
      edtUsername = (EditText) findViewById(R.id.edtUserName);
      edtPassword = (EditText) findViewById(R.id.edtPassword);
        tVSignUp=(TextView) findViewById(R.id.tVSignUp);
        tVSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intentLogin);
            }
        });
//        initDB();
//        addQuestions();
        btnLogin=(Button) findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhap ten dang nhap", Toast.LENGTH_SHORT).show();
                    edtUsername.requestFocus();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhap mat khau ", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                }
                else if(isUser(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("name",username);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tai khoan hoac mat khau sai", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }

    public void btnLogin_onClick(View view) {
    }

    public void tVSignUp_onClick(View view) {
    }
}