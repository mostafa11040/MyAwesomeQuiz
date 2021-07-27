package com.example.myawesomequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
         final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";
         db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
        
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }


    private void fillQuestionsTable() {

        Question q1 = new Question("Who is the best programmer in the world ", "Mostafa Ali", "Elon Musk", "Bill Gates", 1);
        addQuestion(q1);
        Question q2 = new Question("how are you ", "i am fine ", "i am egyptian", "it is 3:00 oclock", 2);
        addQuestion(q2);
        Question q3 = new Question("this is app is ... ", "web", "ios", "Android", 3);
        addQuestion(q3);
        Question q4 = new Question("shopra is country for ... ", "men", "men", "men", 1);
        addQuestion(q4);
        Question q5 = new Question("Mostafa Ali is ... ", "best programmer", "the best in universe", "more than the universe", 3);
        addQuestion(q5);

//        Question q1 = new Question("A is correct", "A", "B", "C", 1);
//        addQuestion(q1);
//        Question q2 = new Question("B is correct", "A", "B", "C", 2);
//        addQuestion(q2);
//        Question q3 = new Question("C is correct", "A", "B", "C", 3);
//        addQuestion(q3);
//        Question q4 = new Question("A is correct again", "A", "B", "C", 1);
//        addQuestion(q4);
//        Question q5 = new Question("B is correct again", "A", "B", "C", 2);
//        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME,null,cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList;
        questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
