package com.sciters.mytheater;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Vincenda Diaz on 1/26/14.
 */
public class SetListSong extends ListActivity {
    String[] demiseseorang = {
            "Tsukimisou",
            "Warning",
            "Tanjoubi no Yuru",
            "Bird",
            "Nage Kiss de Uchi Otose!",
            "Shinkirou",
            "Rider",
            "Seifuku ga Jama wo Suru",
            "Natsu ga Icchatta",
            "Koike",
            "Tsuki no Katachi",
            "Dareka no Tame ni",
            "Medley (Aitakatta, Kimi no Koto ga Suki Dakara, Baby Baby Baby, Ponytail to shushu)",
            "Namida Uri no Shoujo"
    };

    String[] aturanarticinta = {
            "Nagai Hikari",
            "Squall no Aida ni",
            "JK Nemurihime",
            "Kimi ni Autabi Koi wo Suru",
            "Kuroi Tenshi",
            "Heart Gata Virus",
            "Renai Kinshi Jourei",
            "Tsundere!",
            "Manatsu no Christmas Rose",
            "Switch",
            "109",
            "Hikoukigumo",
            "Ano Koro no Sneaker",
            "JKT Sanjou",
            "Namida no Shinkokyuu",
            "Oogue Diamond"
    };

    String[] pajadora = {
            "Shonichi",
            "Hissatsu Teleport",
            "Futari Nori no Jitensha",
            "Tenshi no Shippo",
            "Pajama Drive",
            "Junjou Shugi",
            "Temodemo no Namida",
            "Kagami no Naka no Joan da Arc",
            "Two Years Later",
            "Inochi no Tsukaimichi",
            "Kiss Shite Son Shichatta",
            "Boku no Sakura",
            "Wasshoi J!",
            "Suifu wa Arashi ni Yume wo Miru",
            "Shiroi Shirt"
    };

    String[] matahari = {
        "Dreamin' Girls",
        "Run Run Run",
        "Mirai no Kajitsu (Buah Masa Depan)",
        "Viva! Hurricane",
        "Boku to Juliette to Jet Coaster",
        "Higurashi no Koi",
        "Itoshisha no Defense (Pertahanan dari Cinta)",
        "Takeuchi Senpai (Kakak Kelasku)",
        "Sonna Konna Wake de (Dengan Berbagai Alasan)",
        "Deja vu",
        "Yuuhi wo Miteiruka? (Apakah Kau Melihat Mentari Senja?)",
        "Lay Down!",
        "BINGO",
        "Boku no Taiyou (Matahari Milikku)"
    };

    private Context context;
    private TextView judulLagu;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView (R.layout.activity_setlistsong);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        if (id.contains("Matahari")) {
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matahari) {
            public boolean areAllItemsEnabled()
            {
                return false;
            }
            public boolean isEnabled(int position)
            {
                return false;
            }
        });
        } else if (id.contains("Pajama")) {
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pajadora) {
                public boolean areAllItemsEnabled()
                {
                    return false;
                }
                public boolean isEnabled(int position)
                {
                    return false;
                }
            });
        } else if (id.contains("Aturan")) {
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aturanarticinta) {
                public boolean areAllItemsEnabled()
                {
                    return false;
                }
                public boolean isEnabled(int position)
                {
                    return false;
                }
            });
        } else if (id.contains("Demi")) {
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demiseseorang) {
                public boolean areAllItemsEnabled()
                {
                    return false;
                }
                public boolean isEnabled(int position)
                {
                    return false;
                }
            });
        }


        //judulLagu = (TextView) findViewById(R.id.judullagub);
        //judulLagu.setText(id);
    }


}
