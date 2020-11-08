package chineseCheckers;


import java.util.*;

//представляет собой граф (Graph)
public class GameField {

    private HashMap<Tile, List<Tile>> adjTilesMap;

    public GameField() {
        adjTilesMap = new HashMap<>();
    }

    public Iterator<Tile> getKeyIterator() {
        return adjTilesMap.keySet().iterator();
    }

    public Tile getTile(double x, double y) {
        Set<Tile> tileSet = adjTilesMap.keySet();
        Tile tile = null;
        for (Tile t : tileSet) {
            if (t.equals(new Tile(x, y))){
                tile = t;
                break;
            }
        }
        return tile;
    }

    public List<Tile> getValue(Tile key) {
        return adjTilesMap.get(key);
    }

    public List<Tile> getTiles() {
        List<Tile> list = new ArrayList<>();
        Iterator iterator = adjTilesMap.keySet().iterator();
        while (iterator.hasNext()) {
            Tile tile = (Tile) iterator.next();
            list.add(tile);
        }
        return list;
    }

    public boolean containTile(Tile tile) {
        Iterator iterator = adjTilesMap.keySet().iterator();
        while (iterator.hasNext()) {
            Tile t = (Tile) iterator.next();
            if (tile.getX() == t.getX() && tile.getY() == t.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean defaultContain(Tile tile) {
        return adjTilesMap.containsKey(tile);
    }

    public void addTile(Tile v) {
        adjTilesMap.putIfAbsent(v, null);
    }

    public void removeTile(Tile v) {
        adjTilesMap.values().stream().forEach(e -> e.remove(v));
        adjTilesMap.remove(v);
    }

    /**
     * Return TRUE if Tile have an adjacent
     *
     * @param v1 - Tile 1
     * @param v2 - Tile 2
     * @return true if adjacent
     */
    public boolean addAdj(Tile v1, Tile v2) {
        boolean isAdded = false;
        if (v1.equals(v2)) {
            return false;
        }
        if (adjTilesMap.containsKey(v1)) {
            addTile(v1);
        }
        if (adjTilesMap.containsKey(v2)) {
            addTile(v2);
        }

        //для первой вершины заполняем связи
        if (adjTilesMap.get(v1) == null) {
            List<Tile> l = new ArrayList<>();
            l.add(v2);
            adjTilesMap.putIfAbsent(v1, l);
            isAdded = true;
        } else {
            if (!adjTilesMap.get(v1).contains(v2)) {
                isAdded = true;
                adjTilesMap.get(v1).add(v2);
            }
        }
        //заполняем связи для второй вершины
        if (adjTilesMap.get(v2) == null) {
            List<Tile> l = new ArrayList<>();
            l.add(v1);
            adjTilesMap.putIfAbsent(v2, l);
            isAdded = true;
        } else {
            if (!adjTilesMap.get(v2).contains(v1)) {
                isAdded = true;
                adjTilesMap.get(v2).add(v1);
            }
        }
        return isAdded;
    }

    //Remove adjacent between vertices
    public boolean removeAdj(Tile v1, Tile v2) {
        if (adjTilesMap.get(v1) != null && adjTilesMap.get(v2) != null) {
            adjTilesMap.get(v1).remove(v2);
            adjTilesMap.get(v2).remove(v1);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdjVertices(Tile v, Tile adjV) {
        return adjTilesMap.get(v).contains(adjV);
    }

    public List<Tile> adjVertices(Tile v) {
        return adjTilesMap.get(v);
    }

    /**
     * Return adjacent vertices of given Tile.
     * TYPE List<Tile>
     *
     * @param v -Tile
     * @return adjVertices
     */
    public List<Tile> getAdjVertices(Tile v) {
        return adjTilesMap.get(v);
    }
        
/*
        public void printGraph() {
            for (Map.Entry entry : adjVerticesMap.entrySet()) {
                Tile v = (Tile) entry.getKey();
                System.out.println(v.label);
                List<Tile> l = (List<Tile>) entry.getValue();
                for (Tile Tile : l) {
                    System.out.println(" Value: " + Tile.label);
                }
            }
        }
        
 */
}

