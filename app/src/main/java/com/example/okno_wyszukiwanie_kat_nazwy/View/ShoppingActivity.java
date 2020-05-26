package com.example.okno_wyszukiwanie_kat_nazwy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.okno_wyszukiwanie_kat_nazwy.Model.ProductModel;
import com.example.okno_wyszukiwanie_kat_nazwy.R;
import com.example.okno_wyszukiwanie_kat_nazwy.ViewModel.DatabaseAdapter;

import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {
    private ArrayList<ProductModel> mShoppingList;
    private RecyclerView mRecyclerView;
    private ShoppingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseAdapter databaseAdapter;

    private Button buttonInsert;
    private EditText editTextName;
    private EditText editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        mShoppingList = new ArrayList<>();
        databaseAdapter = new DatabaseAdapter(ShoppingActivity.this);
        mShoppingList = databaseAdapter.getAllProductsFromDB();
        buildRecycleView();

        setButtons();

    }

    public void setButtons() {
        buttonInsert = findViewById(R.id.button_insert);
        editTextName = findViewById(R.id.insertName);
        editTextQuantity = findViewById(R.id.Quantity);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String quantity = editTextQuantity.getText().toString();
                insertItem(name,quantity);
            }
        });
    }

    public void insertItem(String name,String quantity){
        mShoppingList.add(new ProductModel(name,quantity,String.valueOf(mShoppingList.size())));
        databaseAdapter.insertShoppingListToDB(String.valueOf(mShoppingList.size()),name,quantity);
        mAdapter.notifyItemInserted(mShoppingList.size());
    }

    public void removeItem(int position){
        mShoppingList.remove(position);
        databaseAdapter.removeProductFromDB(mShoppingList);
        mAdapter.notifyItemRemoved(position);
    }

    public void buildRecycleView() {
        mRecyclerView = findViewById(R.id.shoppingList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ShoppingAdapter(mShoppingList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new ShoppingAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int posistion) {
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}
