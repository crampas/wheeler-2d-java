package de.mw.scene2d.swing.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import de.mw.scene2d.model.Edge;
import de.mw.scene2d.model.FloatArray2;
import de.mw.scene2d.model.FloatArray2.Callback;
import de.mw.scene2d.model.Ground;
import de.mw.scene2d.model.TileMap;
import de.mw.scene2d.model.GroundTile;
import de.mw.scene2d.model.IntArray2;
import de.mw.scene2d.model.Point;
import de.mw.scene2d.model.Rect;
import de.mw.scene2d.swing.util.GroundMapSerializer;

@XmlRootElement(name = "SampleGround")
public class SampleGround extends Ground implements Serializable
{
    private File mBasleLocation;
    
    private TileMap mGroundMap;

    private List<Edge>[] mTileNavPathList;
    
    private Properties mGroundProperties = new Properties();
    
    private IntArray2 mNextPointIndex;
    
    private Map<String, BufferedImage> mBackgroundImageMap = new HashMap<String, BufferedImage>();
    
    private File mGroundMapFile;
    
    
    public static SampleGround createSampleGround(File groundFile, File mapFile)
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(SampleGround.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SampleGround ground = (SampleGround)unmarshaller.unmarshal(groundFile);
            
            ground.mBasleLocation = groundFile.getParentFile();
            
            InputStream mapStream = new FileInputStream(mapFile);
            ground.mGroundMap = GroundMapSerializer.readGroundMap(mapStream);
            mapStream.close();
            
            ground.mGroundMapFile = mapFile;
            
            ground.init();

            return ground;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    
    public SampleGround()
    {
    }

    public void init()
    {
        collectEdges();
    }

    
    public File getGroundMapFile()
    {
        return mGroundMapFile;
    }



    
    public SampleGround(String propertiesFileName)
    {
    }
    

    private BufferedImage getBackgroundImage(String imageId)
    {
        try
        {
            BufferedImage image = mBackgroundImageMap.get(imageId);
            if(image == null)
            {
                File imageFile = new File(mBasleLocation, imageId);
                image = ImageIO.read(imageFile);
                mBackgroundImageMap.put(imageId, image);
            }
            return image;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    
    public void drawTile(Graphics2D graphics, int x0, int y0, int x1, int y1, int tileIndex)
    {
    	if (tileIndex < 0)
    		return;
    	
        GroundTile tile = this.tile[tileIndex];
        BufferedImage backgroundImage = getBackgroundImage(tile.imageId);
        
//        int px = (tileIndex % 4) * 100;
//        int py = (tileIndex / 4) * 100;
//        int xOffset = x0 * 10;
//        int yOffset = y0 * 10;
        
        int px = tile.tileIndexX * 100;
        int py = tile.tileIndexY * 100;
        graphics.drawImage(backgroundImage, x0, y0, x1, y1, px, py, px + 100, py + 100, null);
        
    }
    
    public BufferedImage getTileImage(int tileIndex)
    {
        GroundTile tile = this.tile[tileIndex];
        BufferedImage backgroundImage = getBackgroundImage(tile.imageId);

//        int x = (tileIndex % 4) * 100;
//        int y = (tileIndex / 4) * 100;
        
        int x = tile.tileIndexX * 100;
        int y = tile.tileIndexY * 100;

        BufferedImage tileImage = backgroundImage.getSubimage(x, y, 100, 100);
        
        return tileImage;
    }
    
    @Override
    public int getTileIndex(int x, int y)
    {
        return mGroundMap.getTileIndex(x, y);
    }

    public void setTileIndex(int x, int y, int tileIndex)
    {
        mGroundMap.setTileIndex(x, y, tileIndex);
    }
    
    
    public GroundTile getTileByIndex(int x, int y)
    {
        int index = getTileIndex(x, y);
        return tile[index];
    }
    
    private void collectEdges()
    {
        int width = mGroundMap.getWidth();
        int height = mGroundMap.getHeight();
        
        mTileNavPathList = new ArrayList[height * width];
        
        for(int yIndex = 0; yIndex < height; yIndex++)
        {
            float yOffset = yIndex * 10.0f;
            for(int xIndex = 0; xIndex < width; xIndex++)
            {
                float xOffset = xIndex * 10.0f;
                GroundTile tile = getTileByIndex(xIndex, yIndex);
                if(tile != null)
                {
                    for (Edge edge : tile.edge)
                    {
                        Edge newEdge = new Edge(edge);
                        newEdge.move(xOffset, yOffset);
                        addNavPathEdge(newEdge, xIndex, yIndex);
                    }
                }
            }
        }
        
        System.out.println("ground.navEdgeList.count=" + mNavPathEdgeList.size());
        System.out.println("ground.navPointList.size=" + mNavPointEdgeListMap.keySet().size());
        System.out.println("ground.navEdge.dimension=" + width + "x" + height);

        mNavPointList.addAll(mNavPointEdgeListMap.keySet());
    }
    
    public void calcShortPaths()
    {
        
        String shortPathFilename = mGroundProperties.getProperty("ground.shortpath.file");
        if(shortPathFilename != null)
        {
            File nextPointFile = new File(shortPathFilename);
    
            try
            {
                if(nextPointFile.exists())
                {
                    ObjectInputStream input = new ObjectInputStream(new FileInputStream(nextPointFile));
                    mNextPointIndex = (IntArray2)input.readObject();
                    input.close();
                    
    //                {
    //                    int targetIndex = 165;
    //                    int index = 0;
    //                    
    //                    while(index != targetIndex)
    //                    {
    //                        Point p = mNavPointList.get(index);
    //                        System.out.println(p);
    //                        index = mNextPointIndex.get(index, targetIndex);
    //                    }
    //                }
                    
                    
                    return;
                }
            }
            catch(Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
        
        int pointCont = mNavPointEdgeListMap.keySet().size();
        final IntArray2 nextPointIndex = new IntArray2(pointCont, pointCont);
        nextPointIndex.setAll(1000);
        final Point[] pointArray = mNavPointEdgeListMap.keySet().toArray(new Point[pointCont]);
        FloatArray2 d = new FloatArray2(pointCont, pointCont);
        d.forAll(new Callback()
        {
            @Override
            public void process(FloatArray2 array, int i1, int i2)
            {
                if(i1 == i2)
                {
                    array.set(i1, i2, 0f);
                    nextPointIndex.set(i1, i2, i1);
                }
                else
                {
                    array.set(i1, i2, 0.1f * Float.MAX_VALUE);
                    Point p1 = pointArray[i1];
                    Point p2 = pointArray[i2];
                    List<Edge> edgeList = mNavPointEdgeListMap.get(p1);
                    if(edgeList != null)
                    {
                        for (Edge edge : edgeList)
                        {
                            Point endPoint = edge.p1;
                            if(endPoint.equals(p2))
                            {
                                array.set(i1, i2, 1f);
                                nextPointIndex.set(i1, i2, i2);
                            }
                        }
                    }
                }
            }
        });

        System.out.println("Kürzeste Wege");
        
        for(int k = 0; k < pointCont; k++)
        {
            d.forAll(k, new Callback() {
                @Override
                public void process(FloatArray2 d, int i1, int i2, int k)
                {
                    float oldDistance = d.get(i1, i2);
                    float k1 = d.get(i1, k);
                    float k2 = d.get(k, i2);
                    float newDistance = k1 + k2;
                    if(newDistance < oldDistance)
                    {
                        d.set(i1, i2, newDistance);
                        int tok = nextPointIndex.get(i1, k);
                        nextPointIndex.set(i1, i2, tok);
                    }
                }
            });
        }
        
        mNextPointIndex = nextPointIndex;

        
//        {
//            int targetIndex = 165;
//            int index = 0;
//            
//            while(index != targetIndex)
//            {
//                Point p = mNavPointList.get(index);
//                System.out.println(p + " " + d.get(index, targetIndex));
//                index = mNextPointIndex.get(index, targetIndex);
//            }
//        }

        
        if(shortPathFilename != null)
        {
            try
            {
                File nextPointFile = new File(shortPathFilename);
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nextPointFile));
                out.writeObject(nextPointIndex);
                out.close();
            }
            catch(Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    public Point getNextTrackPoint(Point position, Point target)
    {
        int positionIndex = mNavPointList.indexOf(position);
        int targetIndex = mNavPointList.indexOf(target);
        int nextIndex = mNextPointIndex.get(positionIndex, targetIndex);
        if(nextIndex < 0)
        {
            String msg = String.format("uncalculated next point position=%d, target=%d", 
                    positionIndex, targetIndex);
            throw new RuntimeException(msg);
        }
        Point next = mNavPointList.get(nextIndex);
        return next;
    }
    
    public List<Edge> getTileNavPathList(int tileX, int tileY)
    {
        int width = mGroundMap.getWidth();
        int height = mGroundMap.getHeight();
        
        if(tileX < 0 || tileX >= width || 
           tileY < 0 || tileY >= height)
        {
            return null;
        }
        int index = tileX + tileY * width;
        return mTileNavPathList[index];
    }
    
    private void addNavPathEdge(Edge edge, int tileX, int tileY)
    {
        int width = mGroundMap.getWidth();
        int height = mGroundMap.getHeight();

        
        addNavPathEdge(edge);
        int index = tileX + tileY * width;
        if(mTileNavPathList[index] == null)
        {
            mTileNavPathList[index] = new ArrayList<Edge>();
        }
        mTileNavPathList[index].add(edge);
    }


    public TileMap getGroundMap()
    {
        return mGroundMap;
    }

    public void setGroundMap(TileMap groundMap)
    {
        mGroundMap = groundMap;
    }
    
    @Override
    public int getWidth()
    {
        return mGroundMap.getWidth();
    }
    
    @Override
    public int getHeight()
    {
        return mGroundMap.getHeight();
    }


    public File getBasleLocation()
    {
        return mBasleLocation;
    }

}
