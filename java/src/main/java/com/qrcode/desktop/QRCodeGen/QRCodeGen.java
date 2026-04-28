package com.qrcode.desktop.QRCodeGen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGen {

    public String generateImage(String baseUrl, String link) {
        synchronized (this) {
            String dynamicUrl = baseUrl + link;

            // Caminho onde o QR Code será salvo
            String filePath = "java/src/main/resources/com/qrcode/desktop/qrcode_youtube.png";
            String regex = "(java/|src/|main/|resources/)";
            String[] myArray = filePath.split(regex);
            String newPath = String.join("", myArray);
            System.out.println("newPath:" + newPath);
            // Configurações do QR Code
            int width = 300;
            int height = 300;
            String fileType = "png";

            try {
                generateQRCode(dynamicUrl, filePath, width, height, fileType);
                System.out.println("QR Code gerado com sucesso em: " + filePath);
            } catch (WriterException | IOException e) {
                System.err.println("Erro ao gerar o QR Code: " + e.getMessage());
            }
            return filePath;
        }
    }

    private static void generateQRCode(String data, String filePath, int width, int height, String fileType)
            throws WriterException, IOException {

        // Configurações de codificação
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // Nível alto de correção de erro
        hints.put(EncodeHintType.MARGIN, 1); // Margem do QR Code

        // Criar a matriz de bits do QR Code
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        // Salvar a matriz como uma imagem
        Path path = new File(filePath).toPath();

        MatrixToImageWriter.writeToPath(bitMatrix, fileType, path);
    }
}