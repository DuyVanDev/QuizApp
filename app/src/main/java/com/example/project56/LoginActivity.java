package com.example.project56;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //SharedPreferences để lưu trữ phiên đăng nhập dưới dạng key-value
    boolean loggedIn = false;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor; //Editor để chỉnh sửa và lưu trữ dữ liệu trong SharedPreferences.

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

    //
    private boolean isUser(String username, String password) {
        try {
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select * from tbluser where username=? and password = ?",
                    new String[]{username, password});
            c.moveToFirst();
            if (c.getCount() > 0) {

                return true;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        mEditor = mPreferences.edit();
        if (loggedIn) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        db = Database.initDatabase(this, DATABASE_NAME);
        edtUsername = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        tVSignUp = (TextView) findViewById(R.id.tVSignUp);
        tVSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentLogin);
            }
        });
//        initDB();
//        addQuestions();
        btnLogin = (Button) findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhap ten dang nhap", Toast.LENGTH_SHORT).show();
                    edtUsername.requestFocus();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhap mat khau ", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                } else if (isUser(username, password)) {
                    loggedIn = true;
                    mEditor.putBoolean("loggedIn", loggedIn);
                    mEditor.apply();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("name", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Tai khoan hoac mat khau sai", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    //ghi đè hàm Pause và Resume để lưu phiên đăng nhập
    @Override
    protected void onPause() {
        super.onPause();

        if (!loggedIn) {
            mEditor.putBoolean("loggedIn", false);
            mEditor.apply();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loggedIn = mPreferences.getBoolean("loggedIn", false);
        if (loggedIn) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Tạo một đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Khi người dùng nhấn Có, thoát khỏi Activity
                LoginActivity.this.finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Khi người dùng nhấn Không, đóng hộp thoại
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}