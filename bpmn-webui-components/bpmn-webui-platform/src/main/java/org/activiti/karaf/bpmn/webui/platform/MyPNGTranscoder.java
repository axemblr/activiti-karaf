/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.karaf.bpmn.webui.platform;

import java.awt.image.BufferedImage;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.resources.Messages;

/**
 * 
 * @author Srinivasan Chikkala
 *
 */
public class MyPNGTranscoder extends PNGTranscoder {

	public MyPNGTranscoder() {
		super();
	}


    private WriteAdapter getWriteAdapter(String className) {
        WriteAdapter adapter;
        try {
            Class clazz = this.getClass().forName(className);
            adapter = (WriteAdapter)clazz.newInstance();
            return adapter;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
    
    /**
     * Writes the specified image to the specified output.
     * @param img the image to write
     * @param output the output where to store the image
     * @throws TranscoderException if an error occured while storing the image
     */
    public void writeImage(BufferedImage img, TranscoderOutput output)
            throws TranscoderException {

        OutputStream ostream = output.getOutputStream();
        if (ostream == null) {
            throw new TranscoderException(
                Messages.formatMessage("png.badoutput", null));
        }

        //
        // This is a trick so that viewers which do not support the alpha
        // channel will see a white background (and not a black one).
        //
        boolean forceTransparentWhite = false;

        if (hints.containsKey(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE)) {
            forceTransparentWhite =
                ((Boolean)hints.get
                 (PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE)).booleanValue();
        }

        if (forceTransparentWhite) {
            SinglePixelPackedSampleModel sppsm;
            sppsm = (SinglePixelPackedSampleModel)img.getSampleModel();
            forceTransparentWhite(img, sppsm);
        }

        WriteAdapter adapter = getWriteAdapter(
                "org.apache.batik.ext.awt.image.codec.png.PNGTranscoderInternalCodecWriteAdapter");
        if (adapter == null) {
            adapter = getWriteAdapter(
                "org.apache.batik.transcoder.image.PNGTranscoderImageIOWriteAdapter");
        }
        if (adapter == null) {
            throw new TranscoderException(
                    "Could not write PNG file because no WriteAdapter is availble");
        }
        adapter.writeImage(this, img, output);
    }
}
