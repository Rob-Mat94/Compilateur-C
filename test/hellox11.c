int main() {
  Display *d;
  Window w;
  XEvent e;
  char *msg = "Hello, World!";
  int s;

  d = XOpenDisplay(((void*)0));
  if (d == ((void*)0)) {
    fprintf(__stderrp, "Cannot open display\n");
    exit(1);
  }

  s = (((_XPrivDisplay)(d))->default_screen);
  w = XCreateSimpleWindow(d, ((&((_XPrivDisplay)(d))->screens[s])->root), 10, 10, 100, 100, 1,
     ((&((_XPrivDisplay)(d))->screens[s])->black_pixel), ((&((_XPrivDisplay)(d))->screens[s])->white_pixel));
  XSelectInput(d, w, (1L<<15) | (1L<<0));
  XMapWindow(d, w);

  while (1) {
    XNextEvent(d, &e);
    if (e.type == 12) {
      XFillRectangle(d, w, ((&((_XPrivDisplay)(d))->screens[s])->default_gc), 20, 20, 10, 10);
      XDrawString(d, w, ((&((_XPrivDisplay)(d))->screens[s])->default_gc), 10, 50, msg, strlen(msg));
    }
    if (e.type == 2)
      break;
  }

  XCloseDisplay(d);
  return 0;
}
