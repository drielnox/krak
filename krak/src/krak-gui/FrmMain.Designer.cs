namespace Krak.Gui
{
    partial class FrmMain
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.GroupBox gbGeneral;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmMain));
            System.Windows.Forms.Label lblSoftware;
            System.Windows.Forms.GroupBox gbDictionary;
            System.Windows.Forms.GroupBox gbBruteForce;
            System.Windows.Forms.GroupBox gbExecution;
            System.Windows.Forms.GroupBox gbAction;
            this.button2 = new System.Windows.Forms.Button();
            this.textBox2 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.lblSoftwarePath = new System.Windows.Forms.Label();
            this.cboSoftware = new System.Windows.Forms.ComboBox();
            this.btnBrowseDictionary = new System.Windows.Forms.Button();
            this.txtDictionaryPath = new System.Windows.Forms.TextBox();
            this.lblDictionaryPath = new System.Windows.Forms.Label();
            this.chkDictionaryFirst = new System.Windows.Forms.CheckBox();
            this.lblCombinations = new System.Windows.Forms.Label();
            this.lblTotalCombinations = new System.Windows.Forms.Label();
            this.btnReset = new System.Windows.Forms.Button();
            this.textBox5 = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.lblMaxLength = new System.Windows.Forms.Label();
            this.nudMinLength = new System.Windows.Forms.NumericUpDown();
            this.lblMinLength = new System.Windows.Forms.Label();
            this.textBox4 = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.lblCommandLine = new System.Windows.Forms.Label();
            this.btnAbort = new System.Windows.Forms.Button();
            this.btnStart = new System.Windows.Forms.Button();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.programToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.lbEvents = new System.Windows.Forms.ListBox();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            gbGeneral = new System.Windows.Forms.GroupBox();
            lblSoftware = new System.Windows.Forms.Label();
            gbDictionary = new System.Windows.Forms.GroupBox();
            gbBruteForce = new System.Windows.Forms.GroupBox();
            gbExecution = new System.Windows.Forms.GroupBox();
            gbAction = new System.Windows.Forms.GroupBox();
            gbGeneral.SuspendLayout();
            gbDictionary.SuspendLayout();
            gbBruteForce.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMinLength)).BeginInit();
            gbExecution.SuspendLayout();
            gbAction.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // gbGeneral
            // 
            resources.ApplyResources(gbGeneral, "gbGeneral");
            gbGeneral.Controls.Add(this.button2);
            gbGeneral.Controls.Add(this.textBox2);
            gbGeneral.Controls.Add(this.label1);
            gbGeneral.Controls.Add(this.button1);
            gbGeneral.Controls.Add(this.textBox1);
            gbGeneral.Controls.Add(this.lblSoftwarePath);
            gbGeneral.Controls.Add(this.cboSoftware);
            gbGeneral.Controls.Add(lblSoftware);
            gbGeneral.Name = "gbGeneral";
            gbGeneral.TabStop = false;
            // 
            // button2
            // 
            resources.ApplyResources(this.button2, "button2");
            this.button2.Name = "button2";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // textBox2
            // 
            resources.ApplyResources(this.textBox2, "textBox2");
            this.textBox2.Name = "textBox2";
            // 
            // label1
            // 
            resources.ApplyResources(this.label1, "label1");
            this.label1.Name = "label1";
            // 
            // button1
            // 
            resources.ApplyResources(this.button1, "button1");
            this.button1.Name = "button1";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // textBox1
            // 
            resources.ApplyResources(this.textBox1, "textBox1");
            this.textBox1.Name = "textBox1";
            // 
            // lblSoftwarePath
            // 
            resources.ApplyResources(this.lblSoftwarePath, "lblSoftwarePath");
            this.lblSoftwarePath.Name = "lblSoftwarePath";
            // 
            // cboSoftware
            // 
            resources.ApplyResources(this.cboSoftware, "cboSoftware");
            this.cboSoftware.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cboSoftware.FormattingEnabled = true;
            this.cboSoftware.Name = "cboSoftware";
            // 
            // lblSoftware
            // 
            resources.ApplyResources(lblSoftware, "lblSoftware");
            lblSoftware.Name = "lblSoftware";
            // 
            // gbDictionary
            // 
            resources.ApplyResources(gbDictionary, "gbDictionary");
            gbDictionary.Controls.Add(this.btnBrowseDictionary);
            gbDictionary.Controls.Add(this.txtDictionaryPath);
            gbDictionary.Controls.Add(this.lblDictionaryPath);
            gbDictionary.Controls.Add(this.chkDictionaryFirst);
            gbDictionary.Name = "gbDictionary";
            gbDictionary.TabStop = false;
            // 
            // btnBrowseDictionary
            // 
            resources.ApplyResources(this.btnBrowseDictionary, "btnBrowseDictionary");
            this.btnBrowseDictionary.Name = "btnBrowseDictionary";
            this.btnBrowseDictionary.UseVisualStyleBackColor = true;
            // 
            // txtDictionaryPath
            // 
            resources.ApplyResources(this.txtDictionaryPath, "txtDictionaryPath");
            this.txtDictionaryPath.Name = "txtDictionaryPath";
            this.txtDictionaryPath.ReadOnly = true;
            // 
            // lblDictionaryPath
            // 
            resources.ApplyResources(this.lblDictionaryPath, "lblDictionaryPath");
            this.lblDictionaryPath.Name = "lblDictionaryPath";
            // 
            // chkDictionaryFirst
            // 
            resources.ApplyResources(this.chkDictionaryFirst, "chkDictionaryFirst");
            this.chkDictionaryFirst.Name = "chkDictionaryFirst";
            this.chkDictionaryFirst.UseVisualStyleBackColor = true;
            // 
            // gbBruteForce
            // 
            resources.ApplyResources(gbBruteForce, "gbBruteForce");
            gbBruteForce.Controls.Add(this.lblCombinations);
            gbBruteForce.Controls.Add(this.lblTotalCombinations);
            gbBruteForce.Controls.Add(this.btnReset);
            gbBruteForce.Controls.Add(this.textBox5);
            gbBruteForce.Controls.Add(this.label3);
            gbBruteForce.Controls.Add(this.numericUpDown1);
            gbBruteForce.Controls.Add(this.lblMaxLength);
            gbBruteForce.Controls.Add(this.nudMinLength);
            gbBruteForce.Controls.Add(this.lblMinLength);
            gbBruteForce.Name = "gbBruteForce";
            gbBruteForce.TabStop = false;
            // 
            // lblCombinations
            // 
            resources.ApplyResources(this.lblCombinations, "lblCombinations");
            this.lblCombinations.Name = "lblCombinations";
            // 
            // lblTotalCombinations
            // 
            resources.ApplyResources(this.lblTotalCombinations, "lblTotalCombinations");
            this.lblTotalCombinations.Name = "lblTotalCombinations";
            // 
            // btnReset
            // 
            resources.ApplyResources(this.btnReset, "btnReset");
            this.btnReset.Name = "btnReset";
            this.btnReset.UseVisualStyleBackColor = true;
            // 
            // textBox5
            // 
            resources.ApplyResources(this.textBox5, "textBox5");
            this.textBox5.Name = "textBox5";
            // 
            // label3
            // 
            resources.ApplyResources(this.label3, "label3");
            this.label3.Name = "label3";
            // 
            // numericUpDown1
            // 
            resources.ApplyResources(this.numericUpDown1, "numericUpDown1");
            this.numericUpDown1.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Value = new decimal(new int[] {
            3,
            0,
            0,
            0});
            // 
            // lblMaxLength
            // 
            resources.ApplyResources(this.lblMaxLength, "lblMaxLength");
            this.lblMaxLength.Name = "lblMaxLength";
            // 
            // nudMinLength
            // 
            resources.ApplyResources(this.nudMinLength, "nudMinLength");
            this.nudMinLength.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.nudMinLength.Name = "nudMinLength";
            this.nudMinLength.ReadOnly = true;
            this.nudMinLength.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // lblMinLength
            // 
            resources.ApplyResources(this.lblMinLength, "lblMinLength");
            this.lblMinLength.Name = "lblMinLength";
            // 
            // gbExecution
            // 
            resources.ApplyResources(gbExecution, "gbExecution");
            gbExecution.Controls.Add(this.textBox4);
            gbExecution.Controls.Add(this.label2);
            gbExecution.Controls.Add(this.textBox3);
            gbExecution.Controls.Add(this.lblCommandLine);
            gbExecution.Name = "gbExecution";
            gbExecution.TabStop = false;
            // 
            // textBox4
            // 
            resources.ApplyResources(this.textBox4, "textBox4");
            this.textBox4.Name = "textBox4";
            // 
            // label2
            // 
            resources.ApplyResources(this.label2, "label2");
            this.label2.Name = "label2";
            // 
            // textBox3
            // 
            resources.ApplyResources(this.textBox3, "textBox3");
            this.textBox3.Name = "textBox3";
            // 
            // lblCommandLine
            // 
            resources.ApplyResources(this.lblCommandLine, "lblCommandLine");
            this.lblCommandLine.Name = "lblCommandLine";
            // 
            // gbAction
            // 
            resources.ApplyResources(gbAction, "gbAction");
            gbAction.Controls.Add(this.btnAbort);
            gbAction.Controls.Add(this.btnStart);
            gbAction.Name = "gbAction";
            gbAction.TabStop = false;
            // 
            // btnAbort
            // 
            resources.ApplyResources(this.btnAbort, "btnAbort");
            this.btnAbort.DialogResult = System.Windows.Forms.DialogResult.Abort;
            this.btnAbort.Name = "btnAbort";
            this.btnAbort.UseVisualStyleBackColor = true;
            // 
            // btnStart
            // 
            resources.ApplyResources(this.btnStart, "btnStart");
            this.btnStart.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.btnStart.Name = "btnStart";
            this.btnStart.UseVisualStyleBackColor = true;
            // 
            // menuStrip1
            // 
            resources.ApplyResources(this.menuStrip1, "menuStrip1");
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.programToolStripMenuItem});
            this.menuStrip1.Name = "menuStrip1";
            // 
            // programToolStripMenuItem
            // 
            resources.ApplyResources(this.programToolStripMenuItem, "programToolStripMenuItem");
            this.programToolStripMenuItem.Name = "programToolStripMenuItem";
            // 
            // lbEvents
            // 
            resources.ApplyResources(this.lbEvents, "lbEvents");
            this.lbEvents.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lbEvents.FormattingEnabled = true;
            this.lbEvents.Name = "lbEvents";
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.FileName = "openFileDialog1";
            resources.ApplyResources(this.openFileDialog1, "openFileDialog1");
            // 
            // FrmMain
            // 
            this.AcceptButton = this.btnStart;
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.CancelButton = this.btnAbort;
            this.Controls.Add(this.lbEvents);
            this.Controls.Add(gbAction);
            this.Controls.Add(gbExecution);
            this.Controls.Add(gbBruteForce);
            this.Controls.Add(gbDictionary);
            this.Controls.Add(gbGeneral);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "FrmMain";
            this.Load += new System.EventHandler(this.FrmMain_Load);
            gbGeneral.ResumeLayout(false);
            gbGeneral.PerformLayout();
            gbDictionary.ResumeLayout(false);
            gbDictionary.PerformLayout();
            gbBruteForce.ResumeLayout(false);
            gbBruteForce.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nudMinLength)).EndInit();
            gbExecution.ResumeLayout(false);
            gbExecution.PerformLayout();
            gbAction.ResumeLayout(false);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem programToolStripMenuItem;
        private System.Windows.Forms.ListBox lbEvents;
        private System.Windows.Forms.Button btnBrowseDictionary;
        private System.Windows.Forms.TextBox txtDictionaryPath;
        private System.Windows.Forms.Label lblDictionaryPath;
        private System.Windows.Forms.CheckBox chkDictionaryFirst;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.TextBox textBox2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label lblSoftwarePath;
        private System.Windows.Forms.ComboBox cboSoftware;
        private System.Windows.Forms.TextBox textBox4;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBox3;
        private System.Windows.Forms.Label lblCommandLine;
        private System.Windows.Forms.Button btnAbort;
        private System.Windows.Forms.Button btnStart;
        private System.Windows.Forms.NumericUpDown numericUpDown1;
        private System.Windows.Forms.Label lblMaxLength;
        private System.Windows.Forms.NumericUpDown nudMinLength;
        private System.Windows.Forms.Label lblMinLength;
        private System.Windows.Forms.Label lblCombinations;
        private System.Windows.Forms.Label lblTotalCombinations;
        private System.Windows.Forms.Button btnReset;
        private System.Windows.Forms.TextBox textBox5;
        private System.Windows.Forms.Label label3;
    }
}

