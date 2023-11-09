package com.example.myapplication.model.classification;

import java.util.ArrayList;
import java.util.List;

public class TrashData {

    public List<Trash> getTrashList(){
        List<Trash> trashList = new ArrayList<>();

        Trash tobacco = new Trash();
        tobacco.setName("담배곽/담배꽁초");
        trashList.add(tobacco);

        Trash plastic = new Trash();
        plastic.setName("플라스틱류(페트병, 우유병, 스티로폼 등)");
        trashList.add(plastic);

        Trash paper = new Trash();
        paper.setName("종이(신문지, 종이컵, 종이팩 등)");
        trashList.add(paper);

        Trash glass = new Trash();
        glass.setName("유리(주류 병, 식기류 등)");
        trashList.add(glass);

        Trash can = new Trash();
        can.setName("캔(음료수 캔, 부탄가스 등)");
        trashList.add(can);

        Trash plasticWaste = new Trash();
        plasticWaste.setName("비닐(위생팩, 과자봉지 등)");
        trashList.add(plasticWaste);

        return trashList;
    }
}
