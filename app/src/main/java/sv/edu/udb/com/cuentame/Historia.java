package sv.edu.udb.com.cuentame;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import sv.edu.udb.com.cuentame.Models.SectionsItem;
import sv.edu.udb.com.cuentame.Models.StoriesItem;

public class Historia extends AppCompatActivity {

        TextView titulo;
        TextView contenido;
        ImageView imagen;
        FloatingActionButton botonSpeak;
        Button siguiente, anterior;

        int totalPages = 0;
        int currentPages =0;
        List<SectionsItem> lista;
        SpeakRequest speakRequest;
        String textToSpeak="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_historia);

            titulo = findViewById(R.id.texto1);
            contenido = findViewById(R.id.texto2);
            imagen = findViewById(R.id.imagencita);
            botonSpeak = findViewById(R.id.buttonfloating);
            siguiente = findViewById(R.id.btn_siguiente);
            anterior = findViewById(R.id.btn_anterior);
            StoriesItem story = commons.historia;
            //  Toast.makeText(this, story.getTitle(), Toast.LENGTH_SHORT).show();
            if(speakRequest == null) {
                speakRequest = new SpeakRequest(this);
            }

            if(story.getSections()!= null)
            {
                lista = story.getSections();
                totalPages = story.getSections().size()-1;

                setData(lista.get(currentPages));
                siguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speakRequest.stopSpeak();
                        currentPages++;
                        if(currentPages>totalPages)
                        {
                            currentPages=0;

                        }
                        setData(lista.get(currentPages));


                    }
                });
                anterior.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speakRequest.stopSpeak();
                        currentPages--;
                        if(currentPages<0)
                        {
                            currentPages=totalPages;

                        }
                        setData(lista.get(currentPages));

                    }
                });


            }

        }

        void setData(SectionsItem sections)
        {
            titulo.setText(sections.getName());
            contenido.setText(sections.getDescription());
            try{
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.downloader(new OkHttp3Downloader(this));
                builder.build().load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/tellme/public/images/" + sections.getUrl().toString())
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background)
                        .into(imagen);
                textToSpeak=sections.getDescription();

                Toast.makeText(this, "Holoooo" + sections.getUrl(), Toast.LENGTH_SHORT).show();

            }catch (Exception e){

                Toast.makeText(this, "Error:" + e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onPause() {
            speakRequest.onDestroy();
            super.onPause();
        }

        public void hablar(View view) {
            if(speakRequest.isSpeaking()){
                speakRequest.stopSpeak();
            }
            speakRequest.speak(textToSpeak);

        }
}
