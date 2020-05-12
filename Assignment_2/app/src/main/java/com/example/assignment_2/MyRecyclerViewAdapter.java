package com.example.assignment_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder>{

    private List<String> questions = new ArrayList<String>();
    private List<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
    private List<String> type = new ArrayList<String>();
    private ArrayList<Set<Integer>> markedOptions = new ArrayList<>();
    private LayoutInflater mInflater;
    static class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioGroup radioGroup;
        View current;
        View lineDivider;
        CustomViewHolder(View view){
            super(view);
            this.lineDivider = view.findViewById(R.id.lineDivider);
            this.question = view.findViewById(R.id.question);
            this.radioGroup = view.findViewById(R.id.radioGroup);
            this.current = view;
        }
    }

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> questions, List<ArrayList<String>> options, List<String> type, ArrayList<Set<Integer>> markedOptions) {
        this.mInflater = LayoutInflater.from(context);
        this.markedOptions = markedOptions;
        this.questions = questions;
        this.options = options;
        this.type = type;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view, parent, false);
        return (new CustomViewHolder(view));
    }

    // add the RadioButton to RadioGroup with Listener
    public void activateLister(@NonNull final CustomViewHolder holder,final int parent,final int optionNumber,String opt){
        RadioButton rb = new RadioButton(holder.current.getContext());
        rb.setText(opt);
        rb.setTextSize(15);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton)view).isChecked()) {
                    markedOptions.get(parent).clear();
                    markedOptions.get(parent).add(optionNumber);
                }
                else
                {
                    markedOptions.get(parent).remove(optionNumber);
                }
            }
        });
        holder.radioGroup.addView(rb);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String local_question = questions.get(position);
        ArrayList<String> local_options = options.get(position);
        String local_type = type.get(position);
        holder.question.setText(local_question);
        if(position == questions.size()-1){
            holder.lineDivider.setVisibility(View.INVISIBLE);
        }
        else{
            holder.lineDivider.setVisibility(View.VISIBLE);
        }
        holder.radioGroup.removeAllViews();
        for(int i = 0 ;i < local_options.size(); i++) {
            activateLister(holder,position,i,local_options.get(i));
        }
        if(markedOptions.get(position).size()>0){
            ArrayList<Integer> temp = new ArrayList<Integer>(markedOptions.get(position));
            ((RadioButton)holder.radioGroup.getChildAt(temp.get(0))).setChecked(true);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return questions.size();
    }


}
