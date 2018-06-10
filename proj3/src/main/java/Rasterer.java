import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *               {lrlon=-122.2104604264636, ullon=-122.30410170759153, w=1085.0, h=566.0, ullat=37.870213571328854, lrlat=37.8318576119893}
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double LonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        int depth = getDepth(LonDPP);


        results.put("depth", depth);
        results.put("render_grid", null);
        results.put("raster_ul_lon", null);
        results.put("raster_ul_lat", null);
        results.put("raster_lr_lon", null);
        results.put("raster_lr_lat", null);
        results.put("query_success", changeGrid(results, params, depth));
        return results;
    }

    private int getDepth(double LonDPP){
        int i = 0;
        double dpp =  (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        while (dpp > LonDPP && i < 7){
            dpp /= 2;
            i++;
        }
        return i;
    }

    private boolean changeGrid(Map<String, Object> results, Map<String, Double> params, int depth){

        double unitLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        double unitLat = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);


        int left = (int)((params.get("ullon") - MapServer.ROOT_ULLON) / unitLon);
        int right = (int)Math.ceil((params.get("lrlon") - MapServer.ROOT_ULLON) / unitLon);
        int up = (int)((MapServer.ROOT_ULLAT - params.get("ullat")) / unitLat);
        int down = (int)Math.ceil((MapServer.ROOT_ULLAT - params.get("lrlat")) / unitLat);


        boolean result = left <= Math.pow(2, depth) && right > left && right >= 0 && up <= Math.pow(2, depth) && down > up && down >= 0;


        if (result) {
            right = (int) Math.min(right, Math.pow(2, depth));
            left = Math.max(0, left);
            up = Math.max(0, up);
            down = (int)Math.min(down, Math.pow(2, depth));


            String[][] render = new String[down - up][right - left];
            for (int i = left; i < right; i++)
                for (int j = up; j < down; j++)
                    render[j - up][i - left] = getPic(depth, i, j);


            results.put("render_grid", render);
            results.put("raster_ul_lon", MapServer.ROOT_ULLON + left * unitLon);
            results.put("raster_ul_lat", MapServer.ROOT_ULLAT - up * unitLat);
            results.put("raster_lr_lon", MapServer.ROOT_ULLON + right * unitLon);
            results.put("raster_lr_lat", MapServer.ROOT_ULLAT - down * unitLat);
        }
        return result;
    }

    private String getPic(int depth, int x, int y){
        return "d" + depth + "_x" + x + "_y" + y + ".png";
    }
}
