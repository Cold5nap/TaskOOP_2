package chineseCheckers;


import java.util.*;

/**
 * adjTilesMap - сам граф
 */
public class GameField {

    //граф
    private final HashMap<Tile, List<Tile>> adjTilesMap;

    public GameField() {
        adjTilesMap = new HashMap<>();
    }

    public Iterator<Tile> getKeyIterator() {
        return adjTilesMap.keySet().iterator();
    }

    public Tile getTile(double x, double y) {
        Set<Tile> tileSet = adjTilesMap.keySet();
        for (Tile t : tileSet) {
            if (t.equals(new Tile(x, y))){
                return t;
            }
        }
        return null;
    }

    public List<Tile> getValue(Tile key) {
        return adjTilesMap.get(key);
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(adjTilesMap.keySet());
    }

    public boolean containTile(Tile tile) {
        for (Tile t : adjTilesMap.keySet()) {
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
        adjTilesMap.values().forEach(e -> e.remove(v));
        adjTilesMap.remove(v);
    }

    /**
     * Добавляет смежность между вершинами.
     * Есть проверки:
     * на то что переданы одинаковые вершины,
     * вершины нет в графе.
     *
     * @param v1 - Tile 1
     * @param v2 - Tile 2
     */
    public void addAdj(Tile v1, Tile v2) {
        if (v1.equals(v2)||!adjTilesMap.containsKey(v1)||!adjTilesMap.containsKey(v2)) {
            return;
        }

        //для первой вершины заполняем ребра
        if (adjTilesMap.get(v1) == null) {
            List<Tile> l = new ArrayList<>();
            l.add(v2);
            adjTilesMap.putIfAbsent(v1, l);
        } else {
            if (!adjTilesMap.get(v1).contains(v2)) {
                adjTilesMap.get(v1).add(v2);
            }
        }
        //заполняем ребра для второй вершины
        if (adjTilesMap.get(v2) == null) {
            List<Tile> l = new ArrayList<>();
            l.add(v1);
            adjTilesMap.putIfAbsent(v2, l);
        } else {
            if (!adjTilesMap.get(v2).contains(v1)) {
                adjTilesMap.get(v2).add(v1);
            }
        }
    }

    /**
     * Удаление ребра между вершинами.
     * @param v1 Первая вершина
     * @param v2 Вторая вершина
     * @return true если ребро имелось и было удалено,
     * false если его не было
     */
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

    /**
     * Возращает лист смежных вершины для tile
     *
     * @param tile -Tile
     * @return List<Tile> adjVertices - лист из смежных вершины
     */
    public List<Tile> getAdjTiles(Tile tile) {
        return adjTilesMap.get(tile);
    }
}

