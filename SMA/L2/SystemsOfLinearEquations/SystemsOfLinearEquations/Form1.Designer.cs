using System.Drawing;

namespace SystemsOfLinearEquations
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
            System.Windows.Forms.DataVisualization.Charting.ChartArea chartArea1 = new System.Windows.Forms.DataVisualization.Charting.ChartArea();
            System.Windows.Forms.DataVisualization.Charting.Legend legend1 = new System.Windows.Forms.DataVisualization.Charting.Legend();
            this.richTextBox1 = new System.Windows.Forms.RichTextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.btn_choleskio = new System.Windows.Forms.Button();
            this.btn_bro2 = new System.Windows.Forms.Button();
            this.btn_bro4 = new System.Windows.Forms.Button();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.tb_br2x1 = new System.Windows.Forms.TextBox();
            this.tb_br2x2 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.btn_opti = new System.Windows.Forms.Button();
            this.rtb_opti = new System.Windows.Forms.RichTextBox();
            this.chart1 = new System.Windows.Forms.DataVisualization.Charting.Chart();
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).BeginInit();
            this.SuspendLayout();
            // 
            // richTextBox1
            // 
            this.richTextBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.richTextBox1.Font = new System.Drawing.Font("Courier New", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(186)));
            this.richTextBox1.Location = new System.Drawing.Point(12, 12);
            this.richTextBox1.Name = "richTextBox1";
            this.richTextBox1.Size = new System.Drawing.Size(824, 620);
            this.richTextBox1.TabIndex = 0;
            this.richTextBox1.Text = "";
            this.richTextBox1.Visible = false;
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Location = new System.Drawing.Point(854, 622);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(154, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "Baigti";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // btn_choleskio
            // 
            this.btn_choleskio.Location = new System.Drawing.Point(865, 12);
            this.btn_choleskio.Name = "btn_choleskio";
            this.btn_choleskio.Size = new System.Drawing.Size(154, 23);
            this.btn_choleskio.TabIndex = 3;
            this.btn_choleskio.Text = "Choleskio metodas";
            this.btn_choleskio.UseVisualStyleBackColor = true;
            this.btn_choleskio.Click += new System.EventHandler(this.btn_choleskio_Click);
            // 
            // btn_bro2
            // 
            this.btn_bro2.Location = new System.Drawing.Point(865, 42);
            this.btn_bro2.Name = "btn_bro2";
            this.btn_bro2.Size = new System.Drawing.Size(154, 23);
            this.btn_bro2.TabIndex = 4;
            this.btn_bro2.Text = "Broideno metodas (2 lygtys)";
            this.btn_bro2.UseVisualStyleBackColor = true;
            this.btn_bro2.Click += new System.EventHandler(this.btn_bro2_Click);
            // 
            // btn_bro4
            // 
            this.btn_bro4.Location = new System.Drawing.Point(865, 131);
            this.btn_bro4.Name = "btn_bro4";
            this.btn_bro4.Size = new System.Drawing.Size(154, 23);
            this.btn_bro4.TabIndex = 5;
            this.btn_bro4.Text = "Broideno metodas (4 lygtys)";
            this.btn_bro4.UseVisualStyleBackColor = true;
            this.btn_bro4.Click += new System.EventHandler(this.btn_bro4_Click);
            // 
            // tb_br2x1
            // 
            this.tb_br2x1.Location = new System.Drawing.Point(862, 98);
            this.tb_br2x1.Name = "tb_br2x1";
            this.tb_br2x1.Size = new System.Drawing.Size(69, 20);
            this.tb_br2x1.TabIndex = 6;
            this.tb_br2x1.Text = "-4";
            // 
            // tb_br2x2
            // 
            this.tb_br2x2.Location = new System.Drawing.Point(947, 98);
            this.tb_br2x2.Name = "tb_br2x2";
            this.tb_br2x2.Size = new System.Drawing.Size(69, 20);
            this.tb_br2x2.TabIndex = 7;
            this.tb_br2x2.Text = "4";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(862, 79);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 13);
            this.label1.TabIndex = 8;
            this.label1.Text = "Pradinis artinys";
            // 
            // btn_opti
            // 
            this.btn_opti.Location = new System.Drawing.Point(862, 183);
            this.btn_opti.Name = "btn_opti";
            this.btn_opti.Size = new System.Drawing.Size(157, 23);
            this.btn_opti.TabIndex = 9;
            this.btn_opti.Text = "Optimizavimo uzdavinys";
            this.btn_opti.UseVisualStyleBackColor = true;
            this.btn_opti.Click += new System.EventHandler(this.btn_opti_Click);
            // 
            // rtb_opti
            // 
            this.rtb_opti.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.rtb_opti.Font = new System.Drawing.Font("Courier New", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(186)));
            this.rtb_opti.Location = new System.Drawing.Point(12, 468);
            this.rtb_opti.Name = "rtb_opti";
            this.rtb_opti.Size = new System.Drawing.Size(824, 164);
            this.rtb_opti.TabIndex = 10;
            this.rtb_opti.Text = "";
            this.rtb_opti.Visible = false;
            // 
            // chart1
            // 
            chartArea1.Name = "ChartArea1";
            this.chart1.ChartAreas.Add(chartArea1);
            legend1.Name = "Legend1";
            this.chart1.Legends.Add(legend1);
            this.chart1.Location = new System.Drawing.Point(12, 12);
            this.chart1.Name = "chart1";
            this.chart1.Size = new System.Drawing.Size(824, 450);
            this.chart1.TabIndex = 11;
            this.chart1.Text = "chart1";
            this.chart1.Visible = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1031, 657);
            this.Controls.Add(this.chart1);
            this.Controls.Add(this.rtb_opti);
            this.Controls.Add(this.richTextBox1);
            this.Controls.Add(this.btn_opti);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.tb_br2x2);
            this.Controls.Add(this.tb_br2x1);
            this.Controls.Add(this.btn_bro4);
            this.Controls.Add(this.btn_bro2);
            this.Controls.Add(this.btn_choleskio);
            this.Controls.Add(this.button1);
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.Text = "Systems of Linear Equations";
            ((System.ComponentModel.ISupportInitialize)(this.chart1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public void PreparareForm(float xmin, float xmax, float ymin, float ymax)
        {

            float x_grids = 20;
            //double xmin = 0; double xmax = 2 * Math.PI;
            chart1.ChartAreas[0].AxisX.MajorGrid.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.LabelStyle.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.MajorTickMark.Interval = (xmax - xmin) / x_grids;
            chart1.ChartAreas[0].AxisX.LabelStyle.Font = new Font("Courier New", 8, FontStyle.Bold);

            float y_grids = 20;
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

        private System.Windows.Forms.RichTextBox richTextBox1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button btn_choleskio;
        private System.Windows.Forms.Button btn_bro2;
        private System.Windows.Forms.Button btn_bro4;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.TextBox tb_br2x1;
        private System.Windows.Forms.TextBox tb_br2x2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btn_opti;
        private System.Windows.Forms.RichTextBox rtb_opti;
        private System.Windows.Forms.DataVisualization.Charting.Chart chart1;
    }
}

