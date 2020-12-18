package com.example.luqis_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.luqis_.Model.AdminOrders;
import com.example.luqis_.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    public boolean showOrders=false;
    AdminOrders adminOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_orders);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef, AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders, UserNewOrdersActivity.UserOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, UserNewOrdersActivity.UserOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserNewOrdersActivity.UserOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {

                        if(Prevalent.currentOnlineUser.getName().equals(model.getProductOwner())){
                            holder.itemView.setVisibility(View.VISIBLE);
                        }
                        if(!Prevalent.currentOnlineUser.getName().equals(model.getProductOwner())){
                            holder.itemView.setVisibility(View.GONE);
                        }
                        holder.userName.setText("Name: "+model.getName());
                        holder.userPhoneNumber.setText("Name: "+model.getPhone());
                        holder.userTotalPrice.setText("Total Ammount : "+model.getTotalAmount());
                        holder.userDateTime.setText("Order at: "+model.getDate()+" "+ model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: "+model.getAddress()+", "+model.getCity());
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(UserNewOrdersActivity.this,UserProductActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                CharSequence options[] =new CharSequence[]{
                                        "Yes",
                                        "No"

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(UserNewOrdersActivity.this);
                                builder.setTitle("Have you shipped this order products?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i==0){
                                            String uID = getRef(position).getKey();
                                            RemoverOrder(uID);
                                        }
                                        else {
                                            finish();
                                        }

                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public UserNewOrdersActivity.UserOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view;
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return new UserNewOrdersActivity.UserOrdersViewHolder(view);
                    }
                };
            ordersList.setAdapter(adapter);
            adapter.startListening();
    }



    public class UserOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView userName, userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrdersBtn;

        public UserOrdersViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
        }
    }
    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }
}
