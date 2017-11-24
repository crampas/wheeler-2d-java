package de.mw.scene2d.swing.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.mw.scene2d.model.TileSet;

public class TileSetSerializer
{
	public static TileSet readTileSet(File file)
	{
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			Element node = doc.getDocumentElement();

			JAXBContext jaxbContext = JAXBContext.newInstance(TileSet.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();			
			JAXBElement<TileSet> tileSetJaxb = unmarshaller.unmarshal(node, TileSet.class);
			TileSet tileSet = tileSetJaxb.getValue();
			
			return tileSet;
		} 
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

	}
}
