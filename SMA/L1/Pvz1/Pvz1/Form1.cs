using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;
using MathNet.Numerics.LinearAlgebra;
using MathNet.Numerics.LinearAlgebra.Factorization;

namespace Pvz1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            Initialize();
        }

        // ---------------------------------------------- PUSIAUKIRTOS METODAS ----------------------------------------------

        Series Fx, X1X2, XMid; // naudojama atvaizduoti f-jai, šaknų rėžiams ir vidiniams taškams


        /// <summary>
        /// Sprendžiama lygtis F(x) = 0
        /// </summary>
        /// <param name="x">funkcijos argumentas</param>
        /// <returns></returns>
        private double F(double x)
        {
            if (rb_fx.Checked)
            {
                // 1.40x^5 + 0.85x^4 - 8.22x^3 - 4.67x^2 + 6.51x + 0.86
                return (1.4 * Math.Pow(x, 5) + 0.85 * Math.Pow(x, 4) - 8.22 * Math.Pow(x, 3) - 4.67 * Math.Pow(x, 2) + 6.51 * x + 0.86);
                //return 5500 - (25000 * x * Math.Pow(1 + x, 6) / (Math.Pow(1 + x, 6) - 1));
            }
            else if (rb_gx.Checked)
            {
                // cos(2x) * e^-((x/2))^2 ; -6 <= x <= 6
                return (double)(Math.Cos(2 * x) * Math.Pow(Math.E, -1 * Math.Pow(x / 2, 2)));
            }
            else if (rb_vh.Checked)
            {
                // pi*h^2*(3*r - h) - 3*V(h); Kai V(h) = 2 ir r = 2
                // pi*x^2*(3*r - x) - 3*V(x); Kai V(x) = 2 ir r = 2
                double vh = 2;
                double r = 2;
                return (double)(Math.PI*x*x*(3 * r - x) - (3 * vh));
            }
            return 0;
        }

        // Atskyrimas skenavimu
        private void button2_Click_1(object sender, EventArgs e)
        {
            ClearForm(); // išvalomi programos duomenys
            PreparareForm(-7, 7, -5, 5);
            // Nubraižoma f-ja, kuriai ieskome saknies
            Fx = chart1.Series.Add("F(x)");
            Fx.ChartType = SeriesChartType.Line;
            // NUO KOKIO X PIESIA
            double x = -6;
            double atstumas = 12;
            int iter_sk = 2000;
            for (int i = 0; i < iter_sk; i++)
            {
                Fx.Points.AddXY(x, F(x)); x = x + atstumas / iter_sk;
            }
            Fx.BorderWidth = 3;

            chart1.Series.Add("Apatinis rėžis");
            chart1.Series[1].MarkerStyle = MarkerStyle.Circle;
            chart1.Series[1].MarkerSize = 8;
            chart1.Series[1].ChartType = SeriesChartType.Point;


            chart1.Series.Add("Viršutinis rėžis");
            chart1.Series[2].MarkerStyle = MarkerStyle.Circle;
            chart1.Series[2].ChartType = SeriesChartType.Point;
            chart1.Series[2].MarkerSize = 8;

            double[] ar = new double[8];
            double[] vr = new double[8];
            double[] artiniai = new double[8];

            double x0 = double.Parse(tb_skenx0.Text);
            double x1 = double.Parse(tb_skenx1.Text);
            double step = double.Parse(tb_skenstep.Text);
            int saknu_count = 0;
            //richTextBox1.AppendText("      I      x0              F(x0)         x1              F(x1)\n");
            for (int i = 1; i < 1000; i++)
            {
                if (Math.Sign(F(x0)) != Math.Sign(F(x0 + step)))
                {
                    //x1 = x0 + step;
                    chart1.Series[1].Points.AddXY(x0, 0);
                    chart1.Series[2].Points.AddXY(x0 + step, 0);
                    ar[saknu_count] = x0;
                    vr[saknu_count] = x0 + step;
                    artiniai[saknu_count] = (x0 + step + x0) / 2;
                    saknu_count++;

                    richTextBox1.AppendText(String.Format("{0}. Rasta {1}-a šaknis intervale [{2,12:f9}; {3,12:f9}]; Artinys = [{4,12:f9}]\n", i, saknu_count, x0, x0+step, artiniai[saknu_count-1]));
                    x0 += step;
                }
                else
                {
                    x0 += step;
                }
                if (x0 >= x1)
                {
                    richTextBox1.AppendText(String.Format("{0}. Pabaiga nes x0(={1}) >= x1(={2})\n", i, x0, x1));
                    richTextBox1.AppendText(String.Format("vr = ["));
                    for (int d = 0; d < vr.Length; d++)
                    {
                        richTextBox1.AppendText(vr[d].ToString());
                        if(d+1 < vr.Length)
                        {
                            richTextBox1.AppendText(", ");
                        }
                    }
                    richTextBox1.AppendText(String.Format("];\n"));
                    {
                        
                    }
                    richTextBox1.AppendText(String.Format("ar = ["));
                    for (int d = 0; d < ar.Length; d++)
                    {
                        richTextBox1.AppendText(ar[d].ToString());
                        if (d + 1 < vr.Length)
                        {
                            richTextBox1.AppendText(", ");
                        }
                    }
                    richTextBox1.AppendText(String.Format("];\n"));

                    richTextBox1.AppendText(String.Format("artin = ["));
                    for (int d = 0; d < artiniai.Length; d++)
                    {
                        richTextBox1.AppendText(artiniai[d].ToString());
                        if (d + 1 < vr.Length)
                        {
                            richTextBox1.AppendText(", ");
                        }
                    }
                    richTextBox1.AppendText(String.Format("];"));
                    break;
                }
            }
        }

        // Skenavimas
        private void button7_Click(object sender, EventArgs e)
        {
            ClearForm(); // išvalomi programos duomenys
            PreparareForm(-7, 7, -5, 5);
            // Nubraižoma f-ja, kuriai ieskome saknies
            Fx = chart1.Series.Add("F(x)");
            Fx.ChartType = SeriesChartType.Line;
            Fx.Color = Color.Black;
            // NUO KOKIO X PIESIA
            double x = -6;
            double atstumas = 12;
            int iter_sk = 2000;
            for (int i = 0; i < iter_sk; i++)
            {
                Fx.Points.AddXY(x, F(x)); x = x + atstumas / iter_sk;
            }
            Fx.BorderWidth = 3;

            chart1.Series.Add("x0");
            chart1.Series[1].MarkerStyle = MarkerStyle.Circle;
            chart1.Series[1].MarkerSize = 8;
            chart1.Series[1].ChartType = SeriesChartType.Point;
            chart1.Series[1].ChartType = SeriesChartType.Line;
            chart1.Series[1].Color = Color.LightGray;


            chart1.Series.Add("x1");
            chart1.Series[2].MarkerStyle = MarkerStyle.Circle;
            chart1.Series[2].ChartType = SeriesChartType.Point;
            chart1.Series[2].ChartType = SeriesChartType.Line;
            chart1.Series[2].Color = Color.Gray;
            chart1.Series[2].MarkerSize = 8;

            chart1.Series.Add("x_mid");
            chart1.Series[3].MarkerStyle = MarkerStyle.Circle;
            chart1.Series[3].ChartType = SeriesChartType.Point;
            chart1.Series[3].ChartType = SeriesChartType.Line;
            chart1.Series[3].Color = Color.Red;
            chart1.Series[3].MarkerSize = 8;

            double x0 = double.Parse(tb_x0.Text.Replace(',', '.'));
            double x1 = double.Parse(tb_x1.Text.Replace(',', '.'));
            double step = 0.10;
            double stepReductionCoef = 2;
            richTextBox1.AppendText("      I      x0              F(x0)         x1              F(x1)\n");
            for (int i = 1; i < 1000; i++)
            {
                if(Math.Sign(F(x0)) != Math.Sign(F(x0+step)))
                {

                    x1 = x0 + step;
                    chart1.Series[2].Points.AddXY(x1, 0);
                    step /= stepReductionCoef;

                } else
                {
                    x0 += step;
                    chart1.Series[1].Points.AddXY(x0, 0);
                }
                richTextBox1.AppendText(String.Format(" {0,6:d}    {1,12:f9}   {2,12:f9}   {3,12:f9}    {4,12:f9}\n", i, x0, F(x0), x1, F(x1)));

                if (Math.Abs(F((x0 + x1)/2)) < 1e-9)
                {
                    chart1.Series[2].Points.AddXY(x0, 0);
                    richTextBox1.AppendText(String.Format("Pabaiga. Rasta saknis (x = {0:f11}, f(x) = {1:f11}) per {2:d} iteracijas(-a).\n", (x0 + x1) / 2, F((x0 + x1) / 2), i));
                    richTextBox1.AppendText(String.Format("{0:f11}\n{1:f11}\n{2:d}\n", (x0 + x1) / 2, F((x0 + x1) / 2), i));
                    chart1.Series[3].Points.AddXY((x0 + x1) / 2, F((x0 + x1) / 2));
                    break;
                }
            }
        }

        // Paprastu iteraciju
        private void button8_Click(object sender, EventArgs e)
        {

            ClearForm(); // išvalomi programos duomenys
            PreparareForm(-7, 7, -5, 5);
            // Nubraižoma f-ja, kuriai ieskome saknies
            Fx = chart1.Series.Add("F(x)");
            Fx.Color = Color.Purple;
            Fx.ChartType = SeriesChartType.Line;
            // NUO KOKIO X PIESIA
            double x = -6;
            double atstumas = 12;
            int iter_sk = 2000;
            for (int i = 0; i < iter_sk; i++)
            {
                Fx.Points.AddXY(x, F(x)); x = x + atstumas / iter_sk;
            }
            Fx.BorderWidth = 3;

            AddYZeroLine();

            Series sk = chart1.Series.Add("x + (F(x) / alpha)");
            sk.MarkerStyle = MarkerStyle.Circle;
            sk.MarkerSize = 8;
            sk.ChartType = SeriesChartType.Point;
            sk.ChartType = SeriesChartType.Line;
            sk.Color = Color.Cyan;


            Series rez = chart1.Series.Add("x_mid");
            rez.MarkerStyle = MarkerStyle.Circle;
            rez.Color = Color.Red;
            rez.MarkerSize = 8;

            double x0 = double.Parse(tb_art.Text);
            double alpha = double.Parse(tb_alpha.Text);
            richTextBox1.AppendText(String.Format(" {0,4:s} {1,16:s} {2,16:s}\n", "I", "x", "F(x)"));
            double xTemp = x0;
            for (int i = 1; i < 1000; i++)
            {
                xTemp = xTemp + (F(xTemp) / alpha);
                sk.Points.AddXY(xTemp, F(xTemp));

                richTextBox1.AppendText(String.Format(" {0,4:d} {1,16:f11} {2,16:f11}\n", i, xTemp, F(xTemp)));

                if (Math.Abs(F(xTemp)) < 1e-9)
                {
                    rez.Points.AddXY(xTemp, 0);
                    richTextBox1.AppendText(String.Format("Pabaiga. Rasta saknis (x = {0:f11}; F(x) = {1:f11}) per {2:d} iteracijas(-a).\n", xTemp, F(xTemp), i));
                    richTextBox1.AppendText(String.Format("{0:f11}\n{1:f11}\n{2:d}\n", xTemp, F(xTemp), i));
                    break;
                }
                if (xTemp > 10 || xTemp < -10)
                {
                    richTextBox1.AppendText("RIP");
                    break;
                }
            }         

        }

        // Kvazi-Niutono
        private void button6_Click(object sender, EventArgs e)
        {
            ClearForm(); // išvalomi programos duomenys
            PreparareForm(-7, 7, -5, 5);
            // Nubraižoma f-ja, kuriai ieskome saknies
            Fx = chart1.Series.Add("F(x)");
            Fx.ChartType = SeriesChartType.Line;
            // NUO KOKIO X PIESIA
            double x = -6;
            double atstumas = 12;
            int iter_sk = 2000;
            for (int i = 0; i < iter_sk; i++)
            {
                Fx.Points.AddXY(x, F(x)); x = x + atstumas / iter_sk;
            }
            Fx.BorderWidth = 3;

            X1X2 = chart1.Series.Add("X1X2");
            X1X2.MarkerStyle = MarkerStyle.Circle;
            X1X2.MarkerSize = 8;
            X1X2.ChartType = SeriesChartType.Point;
            X1X2.ChartType = SeriesChartType.Line;


            XMid = chart1.Series.Add("XMid");
            XMid.MarkerStyle = MarkerStyle.Circle;
            X1X2.ChartType = SeriesChartType.Point;
            X1X2.ChartType = SeriesChartType.Line;
            XMid.MarkerSize = 8;

            double x0 = double.Parse(tb_knx0.Text);
            double x1 = double.Parse(tb_knx1.Text);
            richTextBox1.AppendText(String.Format(" {0,4:s}    {1,16:s} {2,16:s} {2,16:s} {2,16:s}\n", "I", "x1", "F(x1)", "x0", "F(x0)"));
            double xTemp0 = x0;
            double xTemp = x1;
            double xTemp1 = x1;
            for (int i = 1; i < 1000; i++)
            {
                xTemp = xTemp1;
                xTemp1 = xTemp1 - Math.Pow(((F(xTemp1) - F(xTemp0)) / (xTemp1 - xTemp0)), -1) * F(xTemp1);
                xTemp0 = xTemp;

                chart1.Series[1].Points.AddXY(xTemp1, F(xTemp1));

                richTextBox1.AppendText(String.Format(" {0,4:d}    {1,16:f11} {2,16:f11} {2,16:f11} {2,16:f11}\n", i, xTemp1, F(xTemp1), xTemp0, F(xTemp0)));

                if (Math.Abs(F(xTemp1)) < 1e-9)
                {
                    chart1.Series[2].Points.AddXY(xTemp1, 0);
                    richTextBox1.AppendText(String.Format("Pabaiga. Rasta saknis (x = {0:f11}; f(x) = {1:f11}) per {2:d} iteracijas(-a).\n", xTemp1, F(xTemp1), i));
                    richTextBox1.AppendText(String.Format("{0:f11}\n{1:f11}\n{2:d}.\n", xTemp1, F(xTemp1), i));
                    break;
                }
                if (xTemp0 > 10 || xTemp0 < -10)
                {
                    richTextBox1.AppendText("RIP");
                    break;
                }
            }
        }

        // ---------------------------------------------- KITI METODAI ----------------------------------------------

        /// <summary>
        /// Uždaroma programa
        /// </summary>
        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }
        
        /// <summary>
        /// Išvalomas grafikas ir consolė
        /// </summary>
        private void button4_Click(object sender, EventArgs e)
        {
            ClearForm();
        }

        private void rb_fx_CheckedChanged(object sender, EventArgs e)
        {

        }

        public void ClearForm()
        {
            richTextBox1.Clear(); // isvalomas richTextBox1
            // isvalomos visos nubreztos kreives
            chart1.Series.Clear();
        }

        public void AddYZeroLine()
        {
            Series yzero = chart1.Series.Add("");
            yzero.Color = Color.Black;
            yzero.ChartType = SeriesChartType.Line;
            double x = -10;
            double ats = 20;
            int pts = 100;
            for (double i = 0; i < pts; i++)
            {
                yzero.Points.AddXY(x, 0);
                x += ats / pts;

            }
        }
    }
}
