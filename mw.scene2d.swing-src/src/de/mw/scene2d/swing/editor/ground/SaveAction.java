package de.mw.scene2d.swing.editor.ground;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import de.mw.scene2d.swing.game.SampleGround;

public class SaveAction extends AbstractAction
{
    private final GroundEditorFrame mApplication;

    public SaveAction(GroundEditorFrame application)
    {
        super("Speichern");
        mApplication = application;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            SampleGround ground = mApplication.getGround();
            File file = ground.getGroundMapFile();
            if (file.exists())
            {
                String bakFilename = file.toString() + ".bak";
                File bakFile = new File(bakFilename);
                bakFile.delete();
                file.renameTo(bakFile);
            }
            OutputStream output = new FileOutputStream(file);
            ground.getGroundMap().write(output);
            output.close();

            JAXBContext jaxbContext = JAXBContext.newInstance(SampleGround.class);
//            jaxbContext.generateSchema(new MySchemaOutputResolver());
            
//            JAXBElement<SampleGround> jbSampleGround = new JAXBElement<SampleGround>(new QName("SampleGround"), SampleGround.class, ground);
            
//            Marshaller m = jaxbContext.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            m.marshal(ground, new File("SampleGround.xml"));
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    class MySchemaOutputResolver extends SchemaOutputResolver
    {
        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException
        {
            return new StreamResult(new File(suggestedFileName));
        }
    }

}
