package com.roy.tester.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roy.tester.mytester.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */
public class GreenDaoActivity extends Activity {
    PersonDao mPersonDao;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.green_dao_layout);

        mPersonDao = GreenDaoManager.getInstance().getDaoSession().getPersonDao();

        mTextView = (TextView)findViewById(R.id.green_dao_text);
        mTextView.setText("Test");

        Button btnAdd = (Button)findViewById(R.id.green_dao_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("Lily");
                person.setSex("Femal");
                mPersonDao.insert(person);
            }
        });

        Button btnQuery = (Button)findViewById(R.id.green_dao_btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Person> users = mPersonDao.loadAll();
                String userName = "";
                for (int i = 0; i < users.size(); i++) {
                    userName += users.get(i).toString() + ";";
                }

                mTextView.setText(userName);
            }
        });
    }
}
