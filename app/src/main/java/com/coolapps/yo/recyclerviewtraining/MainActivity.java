package com.coolapps.yo.recyclerviewtraining;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private List<ContactModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mData = getData();
        mAdapter = new ContactAdapter(this);
        mAdapter.setList(mData);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private List<ContactModel> getData() {
        final List<ContactModel> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            final boolean shouldAddImageRes = (Math.random() * 10) <= 5; // Some random logic to decide whether to add imageRes
            list.add(new ContactModel("Yogesh" + i, 1234567890 + i, shouldAddImageRes ? R.drawable.background : 0));
        }
        return list;
    }

    @Override
    public void onItemClicked(int position) {
        //TODO: Show contact details page
        final ContactModel model = mData.get(position);
        Toast.makeText(MainActivity.this, "Contact clicked: " + model.getName() + ", " + model.getNumber(), Toast.LENGTH_SHORT).show();
    }
}

class ContactItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mName;
    private TextView mNumber;
    private ContactAdapter.OnItemClickListener mItemClickListener;

    public ContactItemViewHolder(@NonNull View itemView, ContactAdapter.OnItemClickListener listener) {
        super(itemView);
        mName = itemView.findViewById(R.id.name);
        mNumber = itemView.findViewById(R.id.number);
        mItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    public void bind(@NonNull ContactModel model) {
        mName.setText(model.getName());
        mNumber.setText(String.valueOf(model.getNumber()));
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onItemClicked(getAdapterPosition());
    }
}

class ContactItemWithImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mName;
    private TextView mNumber;
    private ImageView mImageView;
    private ContactAdapter.OnItemClickListener mItemClickListener;

    public ContactItemWithImageViewHolder(@NonNull View itemView, ContactAdapter.OnItemClickListener listener) {
        super(itemView);
        mName = itemView.findViewById(R.id.name);
        mNumber = itemView.findViewById(R.id.number);
        mImageView = itemView.findViewById(R.id.image);
        mItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    public void bind(@NonNull ContactModel model) {
        mName.setText(model.getName());
        mNumber.setText(String.valueOf(model.getNumber()));
        mImageView.setImageResource(model.getImageRes());
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onItemClicked(getAdapterPosition());
    }
}

class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_WITHOUT_IMAGE = 0;
    private static final int VIEW_TYPE_WITH_IMAGE = 1;

    private List<ContactModel> mContacts = new ArrayList<>();
    private OnItemClickListener mItemClickListener;

    ContactAdapter(@NonNull OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_WITHOUT_IMAGE) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_view, parent, false);
            return new ContactItemViewHolder(view, mItemClickListener);
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_with_background_view, parent, false);
            return new ContactItemWithImageViewHolder(view, mItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_WITHOUT_IMAGE) {
            ((ContactItemViewHolder) holder).bind(mContacts.get(position));
        } else {
            ((ContactItemWithImageViewHolder) holder).bind(mContacts.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mContacts.get(position).getImageRes() == 0) {
            return VIEW_TYPE_WITHOUT_IMAGE;
        }
        return VIEW_TYPE_WITH_IMAGE;
    }

    public void setList(@NonNull List<ContactModel> list) {
        mContacts.clear();
        mContacts.addAll(list);
        notifyDataSetChanged(); // This is called to notify the adapter that the list has changed.
    }

    public void updateItem(@NonNull ContactModel model, int position) {
        mContacts.set(position, model);
        notifyItemChanged(position); // This is called to notify the adapter that the item at the position has changed.
    }

    interface OnItemClickListener {
        void onItemClicked(int position);
    }
}