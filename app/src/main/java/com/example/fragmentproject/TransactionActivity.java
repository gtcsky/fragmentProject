package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fragmentproject.Bean.Account;
import com.example.fragmentproject.adapter.AccountListViewAdapter;
import com.example.fragmentproject.adapter.AmountSqlHelper;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity  implements View.OnClickListener{
    private String TAG=getClass().getSimpleName();
    private final String tableName="bankAccount";
    private String databaseName="myDB.db";
    private SQLiteDatabase db;
    private ArrayList<Account> accounts;
    private ListView listView;
    private Button addBtn,transferBtn;
    private EditText nameContent, amountContent,tarAccount,oriAccount,transferAmount;
    private AccountListViewAdapter adapter;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        init();
    }

    private void init() {

        addBtn=findViewById(R.id.button_insert);
        transferBtn=findViewById(R.id.transfer_btn);
        nameContent =findViewById(R.id.name_content);
        amountContent =findViewById(R.id.amount_content);
        tarAccount=findViewById(R.id.input_name_content);
        oriAccount=findViewById(R.id.output_name_content);
        transferAmount=findViewById(R.id.amount_input_content);

        AmountSqlHelper amountSqlHelper = new AmountSqlHelper(this, databaseName, null, 1);
        db = amountSqlHelper.getWritableDatabase();
        listView=findViewById(R.id.amount_list_view);
        adapter=new AccountListViewAdapter(this,R.layout.item_account_layout);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(this);
        transferBtn.setOnClickListener(this);
        showAllDataList();

        sharedPreferences=getSharedPreferences("accountInfo",Context.MODE_PRIVATE);
        tarAccount.setText(sharedPreferences.getString("tarAccount",""));
        oriAccount.setText(sharedPreferences.getString("oriAccount",""));
    }

    private void showAllDataList(){
        Cursor cursor = db.rawQuery("select * from " + tableName + " where id>?", new String[]{"0"});
        if (cursor.getCount() == 0) {
            Log.d(TAG, "onCreate: No Record");
            db.execSQL("insert into " + tableName + " (name,amount) values (\"Andy\",10010) ");             //创建两个记录
            db.execSQL("insert into " + tableName + " (name,amount) values (\"Selina\",5000) ");
        } else {
            if (cursor.moveToFirst()) {
                accounts = new ArrayList<>();
                Account account = new Account(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("amount")));
                accounts.add(account);
                while (cursor.moveToNext()) {
                    account = new Account(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("amount")));
                    accounts.add(account);
                }
            }
        }

        adapter.setData(accounts);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        sharedPreferences.edit();
    }

    public boolean isAccountExist(String name){
        if(isEmpty(name)){
            return false;
        }
        for (Account acc : accounts) {
            if(acc.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_insert) {
            String name = nameContent.getText().toString();
            String amountString = amountContent.getText().toString();
            if (isEmpty(name) || isEmpty(name.trim())) {
                userToast("请输入姓名");
                return;
            }
            if (isEmpty(amountString) || isEmpty(amountString.trim())) {
                userToast("请输入金额");
                return;
            }
            name = name.trim();

            if(isAccountExist(name)){
                userToast("用户 "+name+" 已经存在");
                return;
            }

            int amount = Integer.parseInt(amountString.trim());
            try {
                db.execSQL("insert into " + tableName + " (name,amount) values (\"" + name + "\"," + amount + ")");
            } catch (Exception e) {
                userToast("添加数据失败");
            }
            showAllDataList();
        } else if (id == R.id.transfer_btn) {
//            tarAccount,oriAccount,transferAmount;
            String oriName=oriAccount.getText().toString();
            String tarName=tarAccount.getText().toString();
            String transferAmountString=transferAmount.getText().toString();

            if(isEmpty(oriName)||isEmpty(oriName.trim())){
                userToast("转出账户名不能为空");
                return;
            }
            if(isEmpty(tarName)||isEmpty(oriName.trim())){
                userToast("转入账户名不能为空");
                return;
            }
            if(isEmpty(transferAmountString)||isEmpty(transferAmountString.trim())){
                userToast("转账金额不能为空");
                return;
            }
            int amount=Integer.parseInt(transferAmountString.trim());
            if(amount==0){
                userToast("转账金额不能为0");
                return;
            }

            if(!isAccountExist(oriName)){

                userToast("转出账户不存在.");
                return;
            }
            if(!isAccountExist(tarName)){

                userToast("转入账户不存在.");
                return;
            }
            for(Account acc:accounts){
                if(acc.getName().equalsIgnoreCase(oriName)){
                    if( acc.getAmount()<amount){
                        userToast("转出账户余额不足.");
                        return;
                    }
                   break;
                }
            }
            SharedPreferences.Editor edit =sharedPreferences.edit();
            edit.putString("tarAccount",tarName);
            edit.putString("oriAccount",oriName);
            edit.commit();
            db.beginTransaction();
            try {
                db.execSQL("update "+tableName +" set amount=amount+"+amount+" where name=\""+tarName+"\"");
                db.execSQL("update "+tableName +" set amount=amount-"+amount+" where name=\""+oriName+"\"");
                db.setTransactionSuccessful();
                showAllDataList();
                userToast("转账完成");
            }finally {
                db.endTransaction();
            }
        }
    }

    private void userToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}