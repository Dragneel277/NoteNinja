package com.example.noteninja.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteninja.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> content;

    public Adapter(List<String> title,List<String> content){
        this.titles = title;
        this.content = content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.noteTitle.setText(titles.get(position));
        holder.noteContent.setText(content.get(position));
        holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(getRandomColor(),null));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "The Item is Clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        {
            colorCode.add(R.color.White);
            colorCode.add(R.color.Ivory);
            colorCode.add(R.color.LightYellow);
            colorCode.add(R.color.Yellow);
            colorCode.add(R.color.Snow);
            colorCode.add(R.color.LemonChiffon);
            colorCode.add(R.color.Seashell);
            colorCode.add(R.color.PapayaWhip);
            colorCode.add(R.color.MistyRose);
            colorCode.add(R.color.Moccasin);
            colorCode.add(R.color.PeachPuff);
            colorCode.add(R.color.Gold);
            colorCode.add(R.color.Pink);
            colorCode.add(R.color.LightPink);
            colorCode.add(R.color.Orange);
            colorCode.add(R.color.LightSalmon);
            colorCode.add(R.color.DarkOrange);
            colorCode.add(R.color.HotPink);
            colorCode.add(R.color.Tomato);
            colorCode.add(R.color.DeepPink);
            colorCode.add(R.color.Fuchsia);
            colorCode.add(R.color.Magenta);
            colorCode.add(R.color.Red);
            colorCode.add(R.color.OldLace);
            colorCode.add(R.color.AntiqueWhite);
            colorCode.add(R.color.GhostWhite);
            colorCode.add(R.color.MintCream);
            colorCode.add(R.color.Azure);
            colorCode.add(R.color.Honeydew);
            colorCode.add(R.color.AliceBlue);
            colorCode.add(R.color.Khaki);
            colorCode.add(R.color.PaleGoldenrod);
            colorCode.add(R.color.Violet);
            colorCode.add(R.color.DarkSalmon);
            colorCode.add(R.color.Lavender);
            colorCode.add(R.color.LightCyan);
            colorCode.add(R.color.BurlyWood);
            colorCode.add(R.color.Plum);
            colorCode.add(R.color.Gainsboro);
            colorCode.add(R.color.Crimson);
            colorCode.add(R.color.PaleVioletRed);
            colorCode.add(R.color.Goldenrod);
            colorCode.add(R.color.Thistle);
            colorCode.add(R.color.LightGrey);
            colorCode.add(R.color.Tan);
            colorCode.add(R.color.IndianRed);
            colorCode.add(R.color.MediumVioletRed);
            colorCode.add(R.color.Silver);
            colorCode.add(R.color.DarkKhaki);
            colorCode.add(R.color.MediumOrchid);
            colorCode.add(R.color.DarkGoldenrod);
            colorCode.add(R.color.FireBrick);
            colorCode.add(R.color.PowderBlue);
            colorCode.add(R.color.LightSteelBlue);
            colorCode.add(R.color.PaleTurquoise);
            colorCode.add(R.color.GreenYellow);
            colorCode.add(R.color.LightBlue);
            colorCode.add(R.color.DarkGray);
            colorCode.add(R.color.Sienna);
            colorCode.add(R.color.YellowGreen);
            colorCode.add(R.color.DarkOrchid);
            colorCode.add(R.color.PaleGreen);
            colorCode.add(R.color.DarkViolet);
            colorCode.add(R.color.MediumPurple);
            colorCode.add(R.color.LightGreen);
            colorCode.add(R.color.DarkSeaGreen);
            colorCode.add(R.color.SaddleBrown);
            colorCode.add(R.color.DarkMagenta);
            colorCode.add(R.color.DarkRed);
            colorCode.add(R.color.BlueViolet);
            colorCode.add(R.color.LightSkyBlue);
            colorCode.add(R.color.Gray);
            colorCode.add(R.color.Olive);
            colorCode.add(R.color.Purple);
            colorCode.add(R.color.Aquamarine);
            colorCode.add(R.color.LawnGreen);
            colorCode.add(R.color.MediumSlateBlue);
            colorCode.add(R.color.SlateGray);
            colorCode.add(R.color.DarkOliveGreen);
            colorCode.add(R.color.Indigo);
            colorCode.add(R.color.MediumTurquoise);
            colorCode.add(R.color.DarkSlateBlue);
            colorCode.add(R.color.SteelBlue);
            colorCode.add(R.color.RoyalBlue);
            colorCode.add(R.color.MediumSeaGreen);
            colorCode.add(R.color.LimeGreen);
            colorCode.add(R.color.DarkSlateGray);
            colorCode.add(R.color.SeaGreen);
            colorCode.add(R.color.ForestGreen);
            colorCode.add(R.color.LightSeaGreen);
            colorCode.add(R.color.DodgerBlue);
            colorCode.add(R.color.Aqua);
            colorCode.add(R.color.SpringGreen);
            colorCode.add(R.color.MediumSpringGreen);
            colorCode.add(R.color.DeepSkyBlue);
            colorCode.add(R.color.DarkCyan);
            colorCode.add(R.color.Green);
            colorCode.add(R.color.DarkGreen);
            colorCode.add(R.color.Blue);
            colorCode.add(R.color.DarkBlue);
            colorCode.add(R.color.Navy);
            colorCode.add(R.color.Black);
        } // Colors used

        Random randomColor= new Random();
        int number = randomColor.nextInt(colorCode.size());

        return colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle,noteContent;
        View view;

        CardView mCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard);
            view = itemView;
        }
    }
}
