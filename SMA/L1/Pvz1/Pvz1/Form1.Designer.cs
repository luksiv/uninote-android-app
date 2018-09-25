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
namespace Pvz1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea2 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend2 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            this.chart1 = new System.Windows.Forms.DataVisualization.Charting.Chart();
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.timer2 = new System.Windows.Forms.Timer(this.components);
            this.button4 = new System.Windows.Forms.Button();
            this.timer3 = new System.Windows.Forms.Timer(this.components);
            this.button6 = new System.Windows.Forms.Button();
            this.button7 = new System.Windows.Forms.Button();
            this.button8 = new System.Windows.Forms.Button();
            this.tb_x0 = new System.Windows.Forms.TextBox();
            this.tb_x1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.rb_fx = new System.Windows.Forms.RadioButton();
            this.rb_gx = new System.Windows.Forms.RadioButton();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.tb_art = new System.Windows.Forms.TextBox();
            this.tb_alpha = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.tb_knx0 = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.tb_knx1 = new System.Windows.Forms.TextBox();
            this.rb_vh = new System.Windows.Forms.RadioButton();
            this.button2 = new System.Windows.Forms.Button();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.tb_skenx0 = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.tb_skenx1 = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.tb_skenstep = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).BeginInit();
            this.SuspendLayout();
            // 
            // chart1
            // 
            chartArea2.Name = "ChartArea1";
            this.chart1.ChartAreas.Add(chartArea2);
            legend2.Name = "Legend1";
            this.chart1.Legends.Add(legend2);
            this.chart1.Location = new System.Drawing.Point(12, 12);
            this.chart1.Name = "chart1";
            this.chart1.Size = new System.Drawing.Size(774, 394);
            this.chart1.TabIndex = 0;
            this.chart1.Text = "chart1";
            // 
            // richTextBox1
            // 
            this.richTextBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left)));
            this.richTextBox1.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(186)));
            this.richTextBox1.Location = new System.Drawing.Point(12, 412);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.Size = new System.Drawing.Size(774, 434);
            this.richTextBox1.TabIndex = 1;
            this.richTextBox1.Text = "";
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Location = new System.Drawing.Point(925, 826);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 2;
            this.button1.Text = "Baigti";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button4
            // 
            this.button4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.button4.Location = new System.Drawing.Point(844, 826);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 6;
            this.button4.Text = "Valyti";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(796, 99);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(204, 26);
            this.button6.TabIndex = 8;
            this.button6.Text = "Kvazi-Niutono metodas";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // button7
            // 
            this.button7.Location = new System.Drawing.Point(796, 41);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(204, 23);
            this.button7.TabIndex = 9;
            this.button7.Text = "Skenavimo metodas";
            this.button7.UseVisualStyleBackColor = true;
            this.button7.Click += new System.EventHandler(this.button7_Click);
            // 
            // button8
            // 
            this.button8.Location = new System.Drawing.Point(796, 70);
            this.button8.Name = "button8";
            this.button8.Size = new System.Drawing.Size(204, 23);
            this.button8.TabIndex = 10;
            this.button8.Text = "Paprastųjų iteracijų metodas";
            this.button8.UseVisualStyleBackColor = true;
            this.button8.Click += new System.EventHandler(this.button8_Click);
            // 
            // tb_x0
            // 
            this.tb_x0.Location = new System.Drawing.Point(799, 387);
            this.tb_x0.Name = "tb_x0";
            this.tb_x0.Size = new System.Drawing.Size(100, 20);
            this.tb_x0.TabIndex = 11;
            this.tb_x0.Text = "-2.3714";
            // 
            // tb_x1
            // 
            this.tb_x1.Location = new System.Drawing.Point(799, 429);
            this.tb_x1.Name = "tb_x1";
            this.tb_x1.Size = new System.Drawing.Size(100, 20);
            this.tb_x1.TabIndex = 12;
            this.tb_x1.Text = "-2.0314";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(799, 368);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(113, 13);
            this.label1.TabIndex = 13;
            this.label1.Text = "x0 - tikrinimo intervalas";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(799, 410);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(113, 13);
            this.label2.TabIndex = 14;
            this.label2.Text = "x1 - tikrinimo intervalas";
            // 
            // rb_fx
            // 
            this.rb_fx.AutoSize = true;
            this.rb_fx.Location = new System.Drawing.Point(796, 138);
            this.rb_fx.Name = "rb_fx";
            this.rb_fx.Size = new System.Drawing.Size(82, 17);
            this.rb_fx.TabIndex = 15;
            this.rb_fx.Text = "Funkcija f(x)";
            this.rb_fx.UseVisualStyleBackColor = true;
            this.rb_fx.CheckedChanged += new System.EventHandler(this.rb_fx_CheckedChanged);
            // 
            // rb_gx
            // 
            this.rb_gx.AutoSize = true;
            this.rb_gx.Checked = true;
            this.rb_gx.Location = new System.Drawing.Point(796, 159);
            this.rb_gx.Name = "rb_gx";
            this.rb_gx.Size = new System.Drawing.Size(85, 17);
            this.rb_gx.TabIndex = 16;
            this.rb_gx.TabStop = true;
            this.rb_gx.Text = "Funkcija g(x)";
            this.rb_gx.UseVisualStyleBackColor = true;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(796, 355);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(153, 13);
            this.label3.TabIndex = 17;
            this.label3.Text = "---KINTAMIEJI SKENAVIMUI---";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(796, 452);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(156, 13);
            this.label4.TabIndex = 18;
            this.label4.Text = "---KINTAMIEJI PAP. ITERAC.---";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(793, 470);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(97, 13);
            this.label5.TabIndex = 19;
            this.label5.Text = "Pradinis artinys (x0)";
            // 
            // tb_art
            // 
            this.tb_art.Location = new System.Drawing.Point(796, 486);
            this.tb_art.Name = "tb_art";
            this.tb_art.Size = new System.Drawing.Size(100, 20);
            this.tb_art.TabIndex = 20;
            this.tb_art.Text = "-2.2214";
            // 
            // tb_alpha
            // 
            this.tb_alpha.Location = new System.Drawing.Point(796, 525);
            this.tb_alpha.Name = "tb_alpha";
            this.tb_alpha.Size = new System.Drawing.Size(100, 20);
            this.tb_alpha.TabIndex = 21;
            this.tb_alpha.Text = "-30";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(796, 509);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(73, 13);
            this.label6.TabIndex = 22;
            this.label6.Text = "Alpha reikšme";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(796, 552);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(174, 13);
            this.label7.TabIndex = 23;
            this.label7.Text = "--- KINTAMIEJI KVAZI-NIUTONO---";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(796, 569);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(91, 13);
            this.label8.TabIndex = 24;
            this.label8.Text = "Pirmas artinys (x0)";
            // 
            // tb_knx0
            // 
            this.tb_knx0.Location = new System.Drawing.Point(796, 586);
            this.tb_knx0.Name = "tb_knx0";
            this.tb_knx0.Size = new System.Drawing.Size(100, 20);
            this.tb_knx0.TabIndex = 25;
            this.tb_knx0.Text = "-2.3714";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(796, 613);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(90, 13);
            this.label9.TabIndex = 26;
            this.label9.Text = "Antras artinys (x1)";
            // 
            // tb_knx1
            // 
            this.tb_knx1.Location = new System.Drawing.Point(796, 630);
            this.tb_knx1.Name = "tb_knx1";
            this.tb_knx1.Size = new System.Drawing.Size(100, 20);
            this.tb_knx1.TabIndex = 27;
            this.tb_knx1.Text = "-2.0314";
            // 
            // rb_vh
            // 
            this.rb_vh.AutoSize = true;
            this.rb_vh.Location = new System.Drawing.Point(796, 182);
            this.rb_vh.Name = "rb_vh";
            this.rb_vh.Size = new System.Drawing.Size(87, 17);
            this.rb_vh.TabIndex = 28;
            this.rb_vh.Text = "Funkcija V(h)";
            this.rb_vh.UseVisualStyleBackColor = true;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(796, 12);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(204, 23);
            this.button2.TabIndex = 29;
            this.button2.Text = "Šaknų atskyrimas skenavimo metodu";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click_1);
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(793, 212);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(153, 13);
            this.label10.TabIndex = 30;
            this.label10.Text = "---KINTAMIEJI ATSKYRIMUI---";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(796, 229);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(113, 13);
            this.label11.TabIndex = 32;
            this.label11.Text = "x0 - tikrinimo intervalas";
            // 
            // tb_skenx0
            // 
            this.tb_skenx0.Location = new System.Drawing.Point(796, 248);
            this.tb_skenx0.Name = "tb_skenx0";
            this.tb_skenx0.Size = new System.Drawing.Size(100, 20);
            this.tb_skenx0.TabIndex = 31;
            this.tb_skenx0.Text = "-6.8714";
            this.tb_skenx0.WordWrap = false;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(796, 271);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(113, 13);
            this.label12.TabIndex = 34;
            this.label12.Text = "x1 - tikrinimo intervalas";
            // 
            // tb_skenx1
            // 
            this.tb_skenx1.Location = new System.Drawing.Point(796, 290);
            this.tb_skenx1.Name = "tb_skenx1";
            this.tb_skenx1.Size = new System.Drawing.Size(100, 20);
            this.tb_skenx1.TabIndex = 33;
            this.tb_skenx1.Text = "3.4231";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(796, 313);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(100, 13);
            this.label13.TabIndex = 36;
            this.label13.Text = "Skenavimo žingsnis";
            // 
            // tb_skenstep
            // 
            this.tb_skenstep.Location = new System.Drawing.Point(796, 332);
            this.tb_skenstep.Name = "tb_skenstep";
            this.tb_skenstep.Size = new System.Drawing.Size(100, 20);
            this.tb_skenstep.TabIndex = 35;
            this.tb_skenstep.Text = "0.1";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1010, 861);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.tb_skenstep);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.tb_skenx1);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.tb_skenx0);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.rb_vh);
            this.Controls.Add(this.tb_knx1);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.tb_knx0);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.tb_alpha);
            this.Controls.Add(this.tb_art);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.rb_gx);
            this.Controls.Add(this.rb_fx);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.tb_x1);
            this.Controls.Add(this.tb_x0);
            this.Controls.Add(this.button8);
            this.Controls.Add(this.button7);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.richTextBox1);
            this.Controls.Add(this.chart1);
            this.Name = "Form1";
            this.Text = "Lukas Šivickas IFF-6/8 (23 variantas)";
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }


        /// <summary>
        /// Inicializacijos veiksmai
        /// </summary>
        private void Initialize()
        {
        }

        /// <summary>
        /// Paruošiamas langas vaizdavimui
        /// </summary>
        public void PreparareForm(float xmin, float xmax, float ymin, float ymax)
        {

            float x_grids = 10;
            //double xmin = 0; double xmax = 2 * Math.PI;
            chart1.ChartAreas[0].AxisX.MajorGrid.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.LabelStyle.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.MajorTickMark.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.LabelStyle.Font = new Font("Courier New", 8, FontStyle.Bold);

            float y_grids = 10;
            //double ymin = -1; double ymax = 1;
            chart1.ChartAreas[0].AxisY.MajorGrid.Interval = (ymax - ymin) / y_grids;
            chart1.ChartAreas[0].AxisY.LabelStyle.Interval = (ymax - ymin) / y_grids;
            chart1.ChartAreas[0].AxisY.MajorTickMark.Interval = (ymax - ymin) / y_grids;
            chart1.ChartAreas[0].AxisY.LabelStyle.Font = new Font("Courier New", 8, FontStyle.Bold);

            chart1.ChartAreas[0].AxisX.Minimum = xmin;
            chart1.ChartAreas[0].AxisX.Maximum = xmax;
            chart1.ChartAreas[0].AxisY.Minimum = ymin;
            chart1.ChartAreas[0].AxisY.Maximum = ymax;

            chart1.Legends[0].Font = new Font("Times New Roman", 12, FontStyle.Bold);
            chart1.ChartAreas[0].CursorX.IsUserSelectionEnabled = true;
            chart1.ChartAreas[0].CursorX.Interval = 0.01;
            chart1.ChartAreas[0].CursorY.IsUserSelectionEnabled = true;
            chart1.ChartAreas[0].CursorY.Interval = 0.01;
            
        }
        #endregion
        private System.Windows.Forms.DataVisualization.Charting.Chart chart1;
        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Timer timer2;
        private Button button4;
        private Timer timer3;
        private Button button6;
        private Button button7;
        private Button button8;
        private TextBox tb_x0;
        private TextBox tb_x1;
        private Label label1;
        private Label label2;
        private RadioButton rb_fx;
        private RadioButton rb_gx;
        private Label label3;
        private Label label4;
        private Label label5;
        private TextBox tb_art;
        private TextBox tb_alpha;
        private Label label6;
        private Label label7;
        private Label label8;
        private TextBox tb_knx0;
        private Label label9;
        private TextBox tb_knx1;
        private RadioButton rb_vh;
        private Button button2;
        private Label label10;
        private Label label11;
        private TextBox tb_skenx0;
        private Label label12;
        private TextBox tb_skenx1;
        private Label label13;
        private TextBox tb_skenstep;
    }



}

