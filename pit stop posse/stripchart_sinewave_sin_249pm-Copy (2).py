import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import sys, time, math
import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.mime.image import MIMEImage

xsize=100
   
def data_gen():
    t = data_gen.t
    while True:
       t+=1
       val=100.0*math.sin(t*2.0*3.1415/100.0)
       yield t, val

def run(data):
    # update the data
    t,y = data
    if t>-1:
        xdata.append(t)
        ydata.append(y)
        if t>xsize: # Scroll to the left.
            ax.set_xlim(t-xsize, t)
            
        line.set_data(xdata, ydata)
        

    return line



def on_close_figure(event):
    fig.savefig('Z:\elec291\project 1\image_name.png')
    #fromaddr = "jfadel.varellee@gmail.com"
    #toaddr = "ithatiha002@gmail.com"
    #msg = MIMEMultipart()
    #msg['From'] = fromaddr
    #msg['To'] = toaddr
    #msg['Subject'] = "sinplot"
 
    #body = "her's ur graf buddy"
    #msg.attach(MIMEText(body, 'plain'))

    #attachment = 'image_name.png'
    #fp = open(attachment, 'rb')
    #img = MIMEImage(fp.read())
    #fp.close()

    #msg.attach(img)

    #server = smtplib.SMTP('smtp.gmail.com', 587)
    #server.starttls()
    #server.login(fromaddr, "JadeVarelle$")
    #text = msg.as_string()
    #server.sendmail(fromaddr, toaddr, text)
    #server.quit()
    #print('sent')
    #sys.exit(0)


    
data_gen.t = -1
fig = plt.figure()
fig.canvas.mpl_connect('close_event', on_close_figure)
ax = fig.add_subplot(111)
line, = ax.plot([], [], lw=2)
ax.set_ylim(-100, 100)
ax.set_xlim(0, xsize)
ax.grid()
xdata, ydata = [], []
plt.xlabel('Time, (s)')
plt.ylabel('Temperature, (degrees C)')
plt.title('Reflow Oven Profile')



# Important: Although blit=True makes graphing faster, we need blit=False to prevent
# spurious lines to appear when resizing the stripchart.
ani = animation.FuncAnimation(fig, run, data_gen, blit=False, interval=100, repeat=False)
plt.show()







