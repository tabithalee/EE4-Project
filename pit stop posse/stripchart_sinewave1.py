import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import sys, time, math, serial

ser = serial.Serial( 
    port='COM3', 
    baudrate=9600, 
    parity=serial.PARITY_NONE, 
    stopbits=serial.STOPBITS_TWO, 
    bytesize=serial.EIGHTBITS 
) 
ser.isOpen()


x_stuff = [1,2,3,4]
y_stuff = [1,2,3,4]
xsize=768

test_box = None
   
def data_gen():
    t = data_gen.t
    while True:
       text_str = ser.readline()
       dataaa = text_str.split(',')
       dataaa = dataaa[:-1]
       for i in dataaa:
            t+=1
            val = i.strip(' ')
            y = float(val)
            #print val
            #text_box.set_text(text_str)
       yield t, y
       

def run(data):
    # update the data
    t,y = data
    if t>-1:
        xdata.append(t)
        ydata.append(y)
        if t>xsize: # Scroll to the left.
            ax.set_xlim(t-xsize, t)
        line.set_data(xdata, ydata)

    return line,

def on_close_figure(event):
    sys.exit(0)
    

data_gen.t = -1
fig = plt.figure()
plt.xlabel('Reading (number)')
plt.ylabel('Reading (integer)')
plt.title('Intensity detected by the TSLR1406R')
fig.canvas.mpl_connect('close_event', on_close_figure)
ax = fig.add_subplot(111)
line, = ax.plot([], [], lw=2, color = 'g')
ax.set_ylim(0, 1000)
ax.set_xlim(0, 1536)
ax.grid()
xdata, ydata = [], []
text_box=plt.text(0.5, 0.95, '',
        fontsize=14, verticalalignment='top')
# Important: Although blit=True makes graphing faster, we need blit=False to prevent
# spurious lines to appear when resizing the stripchart.
ani = animation.FuncAnimation(fig, run, data_gen, blit=False, interval=100, repeat=False)
plt.show()
