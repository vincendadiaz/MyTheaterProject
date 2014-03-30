package com.sciters.mytheater.entities;

import android.view.View;

import com.sciters.mytheater.helper.adapter.ComingShowsListViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sciters.mytheater.R;

/**
 * Created by Gilang on 10/5/13.
 */
public class GlobalEntities {
    public static ArrayList<Show> ComingShowArrayList = null;
    public static ArrayList<Show> RecentShowArrayList = null;
    public static ArrayList<ComingShowsListViewAdapter.RecordHolder> RecordHolders = new ArrayList<ComingShowsListViewAdapter.RecordHolder>();
    public static ArrayList<View> CurrentComingShowViews = new ArrayList<View>();

    public static Map<Integer, Integer> ImageReferenceList = createMap();
    public static ArrayList<Member> MembersArrayList = null;

    //temporary
    private static Map<Integer, Integer> createMap() {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        result.put(1, R.drawable.member_mova);
        result.put(2, R.drawable.member_ochi);
        result.put(3, R.drawable.member_cleo);
        result.put(4, R.drawable.member_nurhalimah);
        result.put(5, R.drawable.member_acha);
        result.put(6, R.drawable.member_nichan);
        result.put(7, R.drawable.member_ayana);
        result.put(8, R.drawable.member_beby);
        result.put(9, R.drawable.member_cindy);
        result.put(10, R.drawable.member_yupi);
        result.put(11, R.drawable.member_delima);
        result.put(12, R.drawable.member_della);
        result.put(13, R.drawable.member_delli);
        result.put(14, R.drawable.member_dena);
        result.put(15, R.drawable.member_kinal);
        result.put(16, R.drawable.member_diasta);
        result.put(17, R.drawable.member_uty);
        result.put(18, R.drawable.member_shafa);
        result.put(19, R.drawable.member_frieska);
        result.put(20, R.drawable.member_gaby);
        result.put(21, R.drawable.member_ghaida);
        result.put(22, R.drawable.member_haruka);
        result.put(23, R.drawable.member_kariin);
        result.put(24, R.drawable.member_hanna);
        result.put(25, R.drawable.member_rachel);
        result.put(26, R.drawable.member_jeje);
        result.put(27, R.drawable.member_ve);
        result.put(28, R.drawable.member_lidya);
        result.put(29, R.drawable.member_melody);
        result.put(30, R.drawable.member_nabilah);
        result.put(31, R.drawable.member_dhifa);
        result.put(32, R.drawable.member_nadila);
        result.put(33, R.drawable.member_natalia);
        result.put(34, R.drawable.member_noella);
        result.put(35, R.drawable.member_novinta);
        result.put(36, R.drawable.member_octi);
        result.put(37, R.drawable.member_olive);
        result.put(38, R.drawable.member_sisil);
        result.put(39, R.drawable.member_vienny);
        result.put(40, R.drawable.member_rena);
        result.put(41, R.drawable.member_dhike);
        result.put(42, R.drawable.member_rica);
        result.put(43, R.drawable.member_ikha);
        result.put(44, R.drawable.member_rona);
        result.put(45, R.drawable.member_saktia);
        result.put(46, R.drawable.member_sendy);
        result.put(47, R.drawable.member_shania);
        result.put(48, R.drawable.member_naomi);
        result.put(49, R.drawable.member_sinka);
        result.put(50, R.drawable.member_sonia);
        result.put(51, R.drawable.member_sonya);
        result.put(52, R.drawable.member_stella);
        result.put(53, R.drawable.member_akicha);
        result.put(54, R.drawable.member_vanka);
        result.put(55, R.drawable.member_thalia);
        result.put(56, R.drawable.member_yona);

        return Collections.unmodifiableMap(result);
    }
}
