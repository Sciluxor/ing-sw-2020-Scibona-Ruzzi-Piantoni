package it.polimi.ingsw.model.Map;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapLoaderTest {

        //test that the map load correctly
        //
        @Test
        public void loadMap(){

            MapLoader.loadMap();


        }

        //
        //test that the function for hashmap work well, and that the other variable are read correctly
        //

        @Test
        public void mapLoadedContentCheck(){

            ArrayList<Square> squares = MapLoader.loadMap();
            assert (squares.get(3).getCanAccess().get(Directions.OVEST) == 3);



        }

    }