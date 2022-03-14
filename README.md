Mat:
Used to store values of an image...
A Mat is the thing we call a "matrix" in Mathematics - a rectangular array of quantities set out by
rows and columns. Those "quantities" represent pixels in the case of an image Mat,
(e.g. every element of a matrix can be the color of each pixel in an image Mat).

So how would you go about iterating through a matrix? How about this:

```java

for(int row=0;row<mat.rows();row++){
        for(int col=0;col<mat.cols();col++){
//...do what you want..
//e.g. get the value of the 3rd element of 2nd row
//by mat.get(2,3);
        }
        }
```

You get the value of an element of Mat by using its function get(x)(y), 
where x is the first coordinate (row number) and y is the second coordinate (column number) of the element.
For example to get the 4th element of the 7th row of a BGR image Mat named bgrImageMat, use the get method of Mat to get an array of type double, 
which will have a size of 3, each array element representing each of the Blue, Green, and Red channels of the BGR image format.

```java

double [] bgrColor = bgrImageMat.get();
```

What is the purpose of CvType.CV_8UC4:

OpenCV types can be read the following:

CV_
8U: Unsigned int 8-bit
C4: Four channels.
Thus mRgba = new Mat(height, width, CvType.CV_8UC4); creates a Matrix with four color channels and values in the range 0 to 255.

The reason there are 4 channels (like 4 slices that layer together to make an image of different colours) is to make up the image.
The first 3 in this are R, G, B, and the last is Alpha, which is a value between 0 and 1 representing transparency. 
When these slices combine you get the correct combination of colours.


CV_8U - 8-bit unsigned integers ( 0..255 )
CV_8S - 8-bit signed integers ( -128..127 )
CV_16U - 16-bit unsigned integers ( 0..65535 )
CV_16S - 16-bit signed integers ( -32768..32767 )
CV_32S - 32-bit signed integers ( -2147483648..2147483647 )
CV_32F - 32-bit floating-point numbers ( -FLT_MAX..FLT_MAX, INF, NAN )
CV_64F - 64-bit floating-point numbers ( -DBL_MAX..DBL_MAX, INF, NAN )
8-bit unsigned integer (uchar)
8-bit signed integer (schar)
16-bit unsigned integer (ushort)
16-bit signed integer (short)
32-bit signed integer (int)
32-bit floating-point number (float)
64-bit floating-point number (double)
enum { CV_8U=0, CV_8S=1, CV_16U=2, CV_16S=3, CV_32S=4, CV_32F=5, CV_64F=6 };

https://docs.opencv.org/2.4/doc/tutorials/core/mat_the_basic_image_container/mat_the_basic_image_container.html#matthebasicimagecontainer