package org.javaq.chartfaces;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import org.javaq.chartfaces.api.IImageDefinition;



public class ImageStreamer implements PhaseListener {
	public static final String IMAGE_CONTENT_PARAM = "imageContent";
	private static final long serialVersionUID = 4527947564911437029L;
	private static final Logger LOGGER = Logger.getLogger(ImageStreamer.class.getName());
	private static final int HTTP_STATUS_SUCCESS = 200;

	@Override
	public void afterPhase(final PhaseEvent phaseEvent) {
		// Nothing to do here
	}

	@Override
	public void beforePhase(final PhaseEvent phaseEvent) {
		final FacesContext facesContext = phaseEvent.getFacesContext();
		final Map<String, String> params = facesContext.getExternalContext()
				.getRequestParameterMap();

		if (params.containsKey(ImageStreamer.IMAGE_CONTENT_PARAM)) {
			renderImageContent(facesContext,
					params.get(ImageStreamer.IMAGE_CONTENT_PARAM));
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	private void finalizeResponse(final FacesContext facesContext)
			throws IOException {
		final HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();

		response.setStatus(HTTP_STATUS_SUCCESS);
		response.getOutputStream().flush();
	}

	private void renderImageContent(final FacesContext facesContext,
			final String key) {
		final HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		final ExternalContext extContext = facesContext.getExternalContext();
		final Map<String, Object> map = extContext.getSessionMap();
		final IImageDefinition imageContent = (IImageDefinition) map.get(key);

		if (imageContent != null) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "Streaming image: {0}",
						imageContent.getFilePath());
			}

			BufferedInputStream bis = null;
			try {
				response.setContentType(imageContent.getContentType());
				final InputStream inputStream = new FileInputStream(
						imageContent.getFilePath());
				bis = new BufferedInputStream(
						inputStream);

				final byte[] buffer = new byte[2048];

				int length;
				while ((length = (bis.read(buffer))) >= 0) {
					response.getOutputStream().write(buffer, 0, length);
				}

				finalizeResponse(facesContext);
			} catch (final IOException e) {
				LOGGER.log(Level.WARNING, "Exception in streaming image {0}", imageContent.getFilePath());
			} finally {
				final String filePath = imageContent.getFilePath();
				if (bis != null) {
					try {
						bis.close();
					} catch (final IOException e) {
						LOGGER.warning("Failed closing input stream for file: " + filePath);
					}
				}
				map.remove(key);
				final File file = new File(filePath);
				if (!file.delete()) {
					LOGGER.warning("Failed deleting temporary image file: " + filePath);
				}
				facesContext.responseComplete();
			}
		}
	}
}