/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete.clases;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 *
 * @author KONAHA
 */
public class Imagen {
    private BufferedImage img=null,aux,imgcopia;
    private int red,green,blue,brillo,contraste;
    public Imagen(BufferedImage IMAGEN) {
        this.img= IMAGEN;
        aux= new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
        imgcopia=img;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage EscaladeGrises(BufferedImage img) {
      int width = img.getWidth(); //Ancho de la imagen
      int height = img.getHeight(); //Alto de la imagen
      BufferedImage resultado = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
      for(int y = 0; y < height; y++){
          for(int x = 0; x < width; x++){//Doble for para recorrer toda la imagen
            int p = img.getRGB(x,y);
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff; //Canal Red
            int g = (p>>8)&0xff; //Canal Green
            int b = p&0xff; //Canal Blue
            int avg = (r+g+b)/3;
            p = (a<<24) | (avg<<16) | (avg<<8) | avg;
            resultado.setRGB(x, y, p);//Guardar el promedio de los 3 canales en una imagen.
          }
      }
      return resultado ;
    } 
    //KOHJI ONAJA
    public BufferedImage Invertir(BufferedImage img) {
      int width = img.getWidth(); //Ancho de la imagen
      int height = img.getHeight(); //Alto de la imagen
      BufferedImage resultado = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
      for(int y = 0; y < height; y++){
          for(int x = 0; x < width; x++){//Doble for para recorrer toda la imagen
            int p = img.getRGB(x,y);
            Color color= new Color(p);
            int r = 255-color.getRed();
            int g = 255-color.getGreen();
            int b = 255-color.getBlue();
            color= new Color(r,g,b);
            resultado.setRGB(x, y,color.getRGB());//Guardar el promedio de los 3 canales en una imagen.
          }
      }
      return resultado;
    } 
    public BufferedImage HighPassFilter(int radio)
    {
        if(radio!=0)
        {
            Color color;
            int width = img.getWidth(); //Ancho de la imagen
            int height = img.getHeight(); //Alto de la imagen
            BufferedImage resultado = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            int kernelSize = 2*radio+1;
            double[][] kernel = new double[kernelSize][kernelSize];
            double kernelSum = 0;
            for (int i = 0; i < kernelSize; i++) {
                for (int j = 0; j < kernelSize; j++) {
                    if (i == radio && j == radio) {
                        kernel[i][j] = (kernelSize * kernelSize);
                    }
                    else {
                        kernel[i][j] = -1;
                    }
                }
            }   
            BufferedImage agrandada = CrearBordes(radio);
            for (int x = radio; x<width+radio; x++) {
              {
                for (int y = radio; y < height+radio; y++) {
                  double r=0;
                  double g=0;
                  double b=0; 
                  for (int i = -radio; i <=radio; i++) {
                      for (int j = -radio; j <= radio; j++) {
                          color=new Color(agrandada.getRGB(x+j, y+i));
                          r+=color.getRed()*kernel[i+radio][j+radio];
                          g+=color.getGreen()*kernel[i+radio][j+radio];
                          b+=color.getBlue()*kernel[i+radio][j+radio];
                      }
                  }
                  int r_cor = Math.min(Math.max((int) r, 0), 255);
                  int g_cor = Math.min(Math.max((int) g, 0), 255);
                  int b_cor = Math.min(Math.max((int) b, 0), 255); 
                  color= new Color(r_cor, g_cor, b_cor);
                  resultado.setRGB(x-radio,y-radio,color.getRGB());
                }
              }
            }
            return resultado;
        }
        return img;
    }
    public BufferedImage GaussianBlur(int radio)
    {
      if(radio!=0)
      {
        int width = img.getWidth(); //Ancho de la imagen
        int height = img.getHeight(); //Alto de la imagen
        Color color;
        BufferedImage resultado = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        int size = radio * 2 + 1;
        double[][] kernel = new double[size][size];
        double sigma = radio*0.3;
        double scale = 2 * sigma * sigma;
        for (int i = -radio; i <= radio; i++) {
            for (int j = -radio; j <= radio; j++) {
                double exponent = -(i * i + j * j) / scale;
            double value = (1/(2*sigma*sigma*Math.PI))*Math.exp(exponent);
                kernel[i + radio][j + radio] = value;
            }
        }  
        BufferedImage agrandada = CrearBordes(radio);
        for (int x = radio; x<width+radio; x++) {
          {
            for (int y = radio; y < height+radio; y++) {
                double r=0;
                double g=0;
                double b=0; 
                for (int i = -radio; i <=radio; i++) {
                    for (int j = -radio; j <= radio; j++) {
                        color=new Color(agrandada.getRGB(x+j, y+i));
                        r+=color.getRed()*kernel[i+radio][j+radio];
                        g+=color.getGreen()*kernel[i+radio][j+radio];
                        b+=color.getBlue()*kernel[i+radio][j+radio];
                    }
                }
                color= new Color((int)r, (int)g, (int)b);
                resultado.setRGB(x-radio,y-radio,color.getRGB());
            }
          }
        }
        return resultado;
      }
      return img;
    }
    public BufferedImage CrearBordes(int radio) {
        int width = img.getWidth();
        int height = img.getHeight();
        int newWidth = width + 2 * radio;
        int newHeight = height + 2 * radio;
        BufferedImage enlargedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // Copiar píxeles internos
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                enlargedImage.setRGB(x + radio, y + radio, img.getRGB(x, y));
            }
        }
        // Copiar píxeles de los bordes superior e inferior
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < radio; y++) {
                enlargedImage.setRGB(x, y, enlargedImage.getRGB(x, radio));
                enlargedImage.setRGB(x, y + height + radio, enlargedImage.getRGB(x, height + radio - 1));
            }
        }

        // Copiar píxeles de los bordes izquierdo y derecho
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < radio; x++) {
                enlargedImage.setRGB(x, y, enlargedImage.getRGB(radio, y));
                enlargedImage.setRGB(x + width + radio, y, enlargedImage.getRGB(width + radio - 1, y));
            }
        }
        return enlargedImage;
    }
    //BOX BLUR LUIS MARTINEZ
    public BufferedImage boxBlur(int intensity) 
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int newWidth = width + 2 * intensity;
        int newHeight = height + 2 * intensity;
        
        
        BufferedImage bordes = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) 
        {
            for (int x = 0; x < width; x++) 
            {
                bordes.setRGB(x + intensity, y + intensity, img.getRGB(x, y));
            }
        }
        for (int y = 0; y < newHeight; y++) 
        {
            for (int x = 0; x < intensity; x++) 
            {
                bordes.setRGB(x, y, bordes.getRGB(intensity, y));
                bordes.setRGB(newWidth - 1 - x, y, bordes.getRGB(newWidth - 1 - intensity, y));
            }
        }

        for (int x = 0; x < newWidth; x++) 
        {
            for (int y = 0; y < intensity; y++) 
            {
                bordes.setRGB(x, y, bordes.getRGB(x, intensity));
                bordes.setRGB(x, newHeight - 1 - y, bordes.getRGB(x, newHeight - 1 - intensity));
            }
        }

        BufferedImage imagenFiltrada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for (int j = -intensity; j <= intensity; j++) 
                {
                    for (int i = -intensity; i <= intensity; i++) 
                    {
                        int X = x + intensity + i;
                        int Y = y + intensity + j;

                        Color neighborColor = new Color(bordes.getRGB(X, Y));
                        sumRed += neighborColor.getRed();
                        sumGreen += neighborColor.getGreen();
                        sumBlue += neighborColor.getBlue();
                    }
                }

                int averageRed = sumRed / ((2 * intensity + 1) * (2 * intensity + 1));
                int averageGreen = sumGreen / ((2 * intensity + 1) * (2 * intensity + 1));
                int averageBlue = sumBlue / ((2 * intensity + 1) * (2 * intensity + 1));

                Color filteredColor = new Color(averageRed, averageGreen, averageBlue);
                imagenFiltrada.setRGB(x, y, filteredColor.getRGB());
            }
        }

        return imagenFiltrada;
    }
    
    
    public BufferedImage DenoiseMedia(int intensity) 
    {
        int width = img.getWidth();
        int height = img.getHeight();
    
        BufferedImage imagenFiltrada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = new Color(img.getRGB(x, y));
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
    
                // Calcular la media con los píxeles vecinos
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                int count = 0;
    
                for (int j = -intensity; j <= intensity; j++) 
                {
                    for (int i = -intensity; i <= intensity; i++) 
                    {
                        int X = x + i;
                        int Y = y + j;
    
                        if (X >= 0 && X < width && Y >= 0 && Y < height) 
                        {
                            Color neighborColor = new Color(img.getRGB(X, Y));
                            sumRed += neighborColor.getRed();
                            sumGreen += neighborColor.getGreen();
                            sumBlue += neighborColor.getBlue();
                            count++;
                        }
                    }
                }
    
                int avgRed = sumRed / count;
                int avgGreen = sumGreen / count;
                int avgBlue = sumBlue / count;
                Color filteredColor = new Color(avgRed, avgGreen, avgBlue);
                imagenFiltrada.setRGB(x, y, filteredColor.getRGB());
            }
        }
    
        return imagenFiltrada;
    }
    
    public BufferedImage DenoiseMediana(int intensity) {
        int width = img.getWidth();
        int height = img.getHeight();
    
        BufferedImage imagenFiltrada = new BufferedImage(width, height, img.getType());
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color[] neighborColors = new Color[(intensity * 2 + 1) * (intensity * 2 + 1)];
                int count = 0;
    
                for (int j = -intensity; j <= intensity; j++) {
                    for (int i = -intensity; i <= intensity; i++) {
                        int neighborX = x + i;
                        int neighborY = y + j;
    
                        if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                            neighborColors[count] = new Color(img.getRGB(neighborX, neighborY));
                            count++;
                        }
                    }
                }
    
                Color filteredColor = getMedianColor(neighborColors, count);
                imagenFiltrada.setRGB(x, y, filteredColor.getRGB());
            }
        }
    
        return imagenFiltrada;
    }
    
    private Color getMedianColor(Color[] colors, int count) 
    {
        for (int i = 0; i < count - 1; i++) 
        {
            for (int j = 0; j < count - i - 1; j++) 
            {
                if (colors[j].getRGB() > colors[j + 1].getRGB()) 
                {
                    Color temp = colors[j];
                    colors[j] = colors[j + 1];
                    colors[j + 1] = temp;
                }
            }
        }
        return colors[count / 2];
    }
    
    
    //Hideki Sotero
    public BufferedImage Sobel(int radio) {
       if(radio!=0)
       {
        int width = img.getWidth();
        int height = img.getHeight();
    
        BufferedImage imagenFiltrada = new BufferedImage(width, height, img.getType());
    
        int[][] sobelX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };
    
        int[][] sobelY = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
        };
        
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumXRed = 0;
                int sumXGreen = 0;
                int sumXBlue = 0;
    
                int sumYRed = 0;
                int sumYGreen = 0;
                int sumYBlue = 0;
    
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int pixelX = x + i;
                        int pixelY = y + j;
    
                        Color neighborColor = new Color(img.getRGB(pixelX, pixelY));
                        int neighborRed = neighborColor.getRed();
                        int neighborGreen = neighborColor.getGreen();
                        int neighborBlue = neighborColor.getBlue();
    
                        int sobelXValue = sobelX[j + 1][i + 1];
                        int sobelYValue = sobelY[j + 1][i + 1];
    
                        sumXRed += sobelXValue * neighborRed;
                        sumXGreen += sobelXValue * neighborGreen;
                        sumXBlue += sobelXValue * neighborBlue;
    
                        sumYRed += sobelYValue * neighborRed;
                        sumYGreen += sobelYValue * neighborGreen;
                        sumYBlue += sobelYValue * neighborBlue;
                    }
                }
    
                int magnitudeRed = Math.min((int) Math.sqrt(sumXRed * sumXRed + sumYRed * sumYRed), 255);
                int magnitudeGreen = Math.min((int) Math.sqrt(sumXGreen * sumXGreen + sumYGreen * sumYGreen), 255);
                int magnitudeBlue = Math.min((int) Math.sqrt(sumXBlue * sumXBlue + sumYBlue * sumYBlue), 255);
                
                //A COLOR XD
                Color filteredColor = new Color(magnitudeRed, magnitudeGreen, magnitudeBlue);
                imagenFiltrada.setRGB(x, y, filteredColor.getRGB());
            }
        }
        
        return imagenFiltrada;
       }
       else
       {
           return img;
       }
    }
    
    
    public int getGrayScale(int rgb){
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        
        int gray = (r + g + b) / 3; //Convierte rgb a grises

        return gray;
    }
    
    
    
    //Rodrigo Duffoó
    public BufferedImage Pixelar(int radio)
    {
        int tileSize = radio;
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Aplicar el filtro de mosaico
        for (int y = 0; y < img.getHeight(); y += tileSize) {
            for (int x = 0; x < img.getWidth(); x += tileSize) {
                // Obtener el color promedio del bloque de píxeles
                int avgRGB = getAverageRGB(img, x, y, tileSize);
                // Rellenar el bloque de píxeles con el color promedio
                for (int dy = 0; dy < tileSize; dy++) {
                    for (int dx = 0; dx < tileSize; dx++) {
                        int px = x + dx;
                        int py = y + dy;
                        if (px < img.getWidth() && py < img.getHeight()) {
                            outputImage.setRGB(px, py, avgRGB);
                        }
                    }
                }
            }
        }
        return outputImage;
    }
    private static int getAverageRGB(BufferedImage image, int startX, int startY, int size) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int pixelCount = 0;
        for (int y = startY; y < startY + size; y++) {
            for (int x = startX; x < startX + size; x++) {
                if (x < image.getWidth() && y < image.getHeight()) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb);
                    redSum += color.getRed();
                    greenSum += color.getGreen();
                    blueSum += color.getBlue();
                    pixelCount++;
                }
            }
        }
        int avgRed = redSum / pixelCount;
        int avgGreen = greenSum / pixelCount;
        int avgBlue = blueSum / pixelCount;
        return new Color(avgRed, avgGreen, avgBlue).getRGB();
    }
    public BufferedImage Laplace(int radio)
    {
        if(radio!=0)
        {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int[][] laplaceKernel = {
            { 0, -1, 0 },
            { -1, 4, -1 },
            { 0, -1, 0 }
        };

        // Recorre todos los pixeles de la imagen poniendo el -1 para evitar los bordes
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sum = 0;

                // Aplicar el kernel al bloque 3x3 del pixel actual inicia en -1 y acaba en 1 pq el bloque es 3x3, pueden ser otros valores como 1 a 3 
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = img.getRGB(x + i, y + j);
                        int gray = (rgb >> 16) & 0xFF; // Obtener el componente de rojo para pasarlo a escala de grises
                        sum += gray * laplaceKernel[i + 1][j + 1];
                    }
                }

                // esto es para evitar desbordamientos 
                sum = Math.min(Math.max(sum, 0), 255);

                int outputRGB = (sum << 16) | (sum << 8) | sum;
                outputImage.setRGB(x, y, outputRGB);
            }
        }
        return outputImage;
        }
        else
        {
            return img;
        }
    }
    
}
