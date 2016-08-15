//******************************************************************************
// Copyright (c) 2015, The Regents of the University of California (Regents).
// All Rights Reserved. See LICENSE for license details.
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// RISCV Processor Constants
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//
// Christopher Celio
// 2011 May 28

package boom
package constants
{

import Chisel._
import cde.Parameters

import rocket.Str

trait BOOMDebugConstants
{
   val DEBUG_PRINTF        = false // use the Chisel printf functionality
   val DEBUG_ENABLE_COLOR  = false // provide color to print outs. Requires a VIM plugin to work properly :(
   val COMMIT_LOG_PRINTF   = false // dump commit state, for comparision against ISA sim
   val O3PIPEVIEW_PRINTF   = false // dump trace for O3PipeView from gem5
   val O3_CYCLE_TIME       = (1000)// "cycle" time expected by o3pipeview.py

   // turn off stuff to dramatically reduce Chisel node count
   val DEBUG_PRINTF_LSU    = true && DEBUG_PRINTF
   val DEBUG_PRINTF_ROB    = true && DEBUG_PRINTF
   val DEBUG_PRINTF_TAGE   = true && DEBUG_PRINTF

   if (O3PIPEVIEW_PRINTF) require (!DEBUG_PRINTF && !COMMIT_LOG_PRINTF)

   // color codes for output files
   // if you use VIM to view, you'll need the AnsiEsc plugin.
   // 1 is bold, 2 is background, 4 is underlined
   val blk   = if (DEBUG_ENABLE_COLOR) "\u001b[1;30m" else " "
   val red   = if (DEBUG_ENABLE_COLOR) "\u001b[1;31m" else " "
   val grn   = if (DEBUG_ENABLE_COLOR) "\u001b[1;32m" else " "
   val ylw   = if (DEBUG_ENABLE_COLOR) "\u001b[1;33m" else " "
   val blu   = if (DEBUG_ENABLE_COLOR) "\u001b[1;34m" else " "
   val mgt   = if (DEBUG_ENABLE_COLOR) "\u001b[1;35m" else " "
   val cyn   = if (DEBUG_ENABLE_COLOR) "\u001b[1;36m" else " "
   val wht   = if (DEBUG_ENABLE_COLOR) "\u001b[1;37m" else " "
   val end   = if (DEBUG_ENABLE_COLOR) "\u001b[0m"    else " "

   val b_blk = if (DEBUG_ENABLE_COLOR) "\u001b[2;30m" else " "
   val b_red = if (DEBUG_ENABLE_COLOR) "\u001b[2;31m" else " "
   val b_grn = if (DEBUG_ENABLE_COLOR) "\u001b[2;32m" else " "
   val b_ylw = if (DEBUG_ENABLE_COLOR) "\u001b[2;33m" else " "
   val b_blu = if (DEBUG_ENABLE_COLOR) "\u001b[2;34m" else " "
   val b_mgt = if (DEBUG_ENABLE_COLOR) "\u001b[2;35m" else " "
   val b_cyn = if (DEBUG_ENABLE_COLOR) "\u001b[2;36m" else " "
   val b_wht = if (DEBUG_ENABLE_COLOR) "\u001b[2;37m" else " "

   val u_blk = if (DEBUG_ENABLE_COLOR) "\u001b[4;30m" else " "
   val u_red = if (DEBUG_ENABLE_COLOR) "\u001b[4;31m" else " "
   val u_grn = if (DEBUG_ENABLE_COLOR) "\u001b[4;32m" else " "
   val u_ylw = if (DEBUG_ENABLE_COLOR) "\u001b[4;33m" else " "
   val u_blu = if (DEBUG_ENABLE_COLOR) "\u001b[4;34m" else " "
   val u_mgt = if (DEBUG_ENABLE_COLOR) "\u001b[4;35m" else " "
   val u_cyn = if (DEBUG_ENABLE_COLOR) "\u001b[4;36m" else " "
   val u_wht = if (DEBUG_ENABLE_COLOR) "\u001b[4;37m" else " "
}

trait BrPredConstants
{
   val NOT_TAKEN = Bool(false)
   val TAKEN = Bool(true)
}

trait ScalarOpConstants
{
   val X = BitPat("b?")
   val Y = BitPat("b1")
   val N = BitPat("b0")

   //************************************
   // Extra Constants
   val WATCHDOG_ERR_NO = 0xffff // tohost error number


   //************************************
   // Control Signals

   val s_invalid :: s_valid_1 :: s_valid_2 :: Nil = Enum(UInt(),3)

   // PC Select Signal
   val PC_PLUS4 = UInt(0, 2)  // PC + 4
   val PC_BRJMP = UInt(1, 2)  // brjmp_target
   val PC_JALR  = UInt(2, 2)  // jump_reg_target

   // Branch Type
   val BR_N   = UInt(0, 4)  // Next
   val BR_NE  = UInt(1, 4)  // Branch on NotEqual
   val BR_EQ  = UInt(2, 4)  // Branch on Equal
   val BR_GE  = UInt(3, 4)  // Branch on Greater/Equal
   val BR_GEU = UInt(4, 4)  // Branch on Greater/Equal Unsigned
   val BR_LT  = UInt(5, 4)  // Branch on Less Than
   val BR_LTU = UInt(6, 4)  // Branch on Less Than Unsigned
   val BR_J   = UInt(7, 4)  // Jump
   val BR_JR  = UInt(8, 4)  // Jump Register

   // RS1 Operand Select Signal
   val OP1_RS1 = UInt(0, 2) // Register Source #1
   val OP1_ZERO= UInt(1, 2)
   val OP1_PC  = UInt(2, 2)
   val OP1_X   = BitPat("b??")

   // RS2 Operand Select Signal
   val OP2_RS2 = UInt(0, 3) // Register Source #2
   val OP2_IMM = UInt(1, 3) // immediate
   val OP2_ZERO= UInt(2, 3) // constant 0
   val OP2_FOUR= UInt(3, 3) // constant 4 (for PC+4)
   val OP2_IMMC= UInt(4, 3) // for CSR imm found in RS1
   val OP2_X   = BitPat("b???")

   // Register File Write Enable Signal
   val REN_0   = Bool(false)
   val REN_1   = Bool(true)

   // Is 32b Word or 64b Doubldword?
   val SZ_DW = 1
   val DW_X   = Bool(true) // Bool(xLen==64)
   val DW_32  = Bool(false)
   val DW_64  = Bool(true)
   val DW_XPR = Bool(true) // Bool(xLen==64)

   // Memory Enable Signal
   val MEN_0   = Bool(false)
   val MEN_1   = Bool(true)
   val MEN_X   = Bool(false)

   // Immediate Extend Select
   val IS_I   = UInt(0, 3)  // I-Type  (LD,ALU)
   val IS_S   = UInt(1, 3)  // S-Type  (ST)
   val IS_B   = UInt(2, 3)  // SB-Type (BR)
   val IS_U   = UInt(3, 3)  // U-Type  (LUI/AUIPC)
   val IS_J   = UInt(4, 3)  // UJ-Type (J/JAL)
   val IS_X   = BitPat("b???")


   // Decode Stage Control Signals
   // XXX size depends on usingVec
   val RT_FIX   = UInt(0, 3)
   val RT_FLT   = UInt(1, 3)
   val RT_PAS   = UInt(3, 3) // pass-through (pop1 := lrs1, etc)
   val RT_VEC   = UInt(4, 3)
   val RT_X     = UInt(2, 3) // not-a-register (but shouldn't get a busy-bit, etc.)
                             // TODO rename RT_NAR

   // Micro-op opcodes
   // TODO change micro-op opcodes into using enum
   val UOPC_SZ = 10 // XXX make this 9 if not usingVector
   val uopX    = BitPat.dontCare(UOPC_SZ)
   val uopNOP  = UInt( 0, UOPC_SZ)
   val uopLD   = UInt( 1, UOPC_SZ)
   val uopSTA  = UInt( 2, UOPC_SZ)  // store address generation
   val uopSTD  = UInt( 3, UOPC_SZ)  // store data generation
   val uopLUI  = UInt( 4, UOPC_SZ)

   val uopADDI = UInt( 5, UOPC_SZ)
   val uopANDI = UInt( 6, UOPC_SZ)
   val uopORI  = UInt( 7, UOPC_SZ)
   val uopXORI = UInt( 8, UOPC_SZ)
   val uopSLTI = UInt( 9, UOPC_SZ)
   val uopSLTIU= UInt(10, UOPC_SZ)
   val uopSLLI = UInt(11, UOPC_SZ)
   val uopSRAI = UInt(12, UOPC_SZ)
   val uopSRLI = UInt(13, UOPC_SZ)

   val uopSLL  = UInt(14, UOPC_SZ)
   val uopADD  = UInt(15, UOPC_SZ)
   val uopSUB  = UInt(16, UOPC_SZ)
   val uopSLT  = UInt(17, UOPC_SZ)
   val uopSLTU = UInt(18, UOPC_SZ)
   val uopAND  = UInt(19, UOPC_SZ)
   val uopOR   = UInt(20, UOPC_SZ)
   val uopXOR  = UInt(21, UOPC_SZ)
   val uopSRA  = UInt(22, UOPC_SZ)
   val uopSRL  = UInt(23, UOPC_SZ)

   val uopBEQ  = UInt(24, UOPC_SZ)
   val uopBNE  = UInt(25, UOPC_SZ)
   val uopBGE  = UInt(26, UOPC_SZ)
   val uopBGEU = UInt(27, UOPC_SZ)
   val uopBLT  = UInt(28, UOPC_SZ)
   val uopBLTU = UInt(29, UOPC_SZ)
   val uopCSRRW= UInt(30, UOPC_SZ)
   val uopCSRRS= UInt(31, UOPC_SZ)
   val uopCSRRC= UInt(32, UOPC_SZ)
   val uopCSRRWI=UInt(33, UOPC_SZ)
   val uopCSRRSI=UInt(34, UOPC_SZ)
   val uopCSRRCI=UInt(35, UOPC_SZ)

   val uopJ    = UInt(36, UOPC_SZ)
   val uopJAL  = UInt(37, UOPC_SZ)
   val uopJALR = UInt(38, UOPC_SZ)
   val uopAUIPC= UInt(39, UOPC_SZ)

//   val uopSRET = UInt(40, UOPC_SZ)
   val uopCFLSH= UInt(41, UOPC_SZ)
   val uopFENCE= UInt(42, UOPC_SZ)

   val uopADDIW= UInt(43, UOPC_SZ)
   val uopADDW = UInt(44, UOPC_SZ)
   val uopSUBW = UInt(45, UOPC_SZ)
   val uopSLLIW= UInt(46, UOPC_SZ)
   val uopSLLW = UInt(47, UOPC_SZ)
   val uopSRAIW= UInt(48, UOPC_SZ)
   val uopSRAW = UInt(49, UOPC_SZ)
   val uopSRLIW= UInt(50, UOPC_SZ)
   val uopSRLW = UInt(51, UOPC_SZ)
   val uopMUL  = UInt(52, UOPC_SZ)
   val uopMULH = UInt(53, UOPC_SZ)
   val uopMULHU= UInt(54, UOPC_SZ)
   val uopMULHSU=UInt(55, UOPC_SZ)
   val uopMULW = UInt(56, UOPC_SZ)
   val uopDIV  = UInt(57, UOPC_SZ)
   val uopDIVU = UInt(58, UOPC_SZ)
   val uopREM  = UInt(59, UOPC_SZ)
   val uopREMU = UInt(60, UOPC_SZ)
   val uopDIVW = UInt(61, UOPC_SZ)
   val uopDIVUW= UInt(62, UOPC_SZ)
   val uopREMW = UInt(63, UOPC_SZ)
   val uopREMUW= UInt(64, UOPC_SZ)

   val uopFENCEI    = UInt(65, UOPC_SZ)
   //               = UInt(66, UOPC_SZ)
   val uopAMO_AG    = UInt(67, UOPC_SZ) // AMO-address gen (use normal STD for datagen)

   val uopFMV_S_X   = UInt(68, UOPC_SZ)
   val uopFMV_D_X   = UInt(69, UOPC_SZ)
   val uopFMV_X_S   = UInt(70, UOPC_SZ)
   val uopFMV_X_D   = UInt(71, UOPC_SZ)

   val uopFSGNJ_S   = UInt(72, UOPC_SZ)
   val uopFSGNJ_D   = UInt(73, UOPC_SZ)

   val uopFCVT_S_D  = UInt(74, UOPC_SZ)
   val uopFCVT_D_S  = UInt(75, UOPC_SZ)

   val uopFCVT_S_W  = UInt(76, UOPC_SZ)
   val uopFCVT_S_WU = UInt(77, UOPC_SZ)
   val uopFCVT_S_L  = UInt(78, UOPC_SZ)
   val uopFCVT_S_LU = UInt(79, UOPC_SZ)
   val uopFCVT_D_W  = UInt(80, UOPC_SZ)
   val uopFCVT_D_WU = UInt(81, UOPC_SZ)
   val uopFCVT_D_L  = UInt(82, UOPC_SZ)
   val uopFCVT_D_LU = UInt(83, UOPC_SZ)


   val uopFCVT_W_S  = UInt(84, UOPC_SZ)
   val uopFCVT_WU_S = UInt(85, UOPC_SZ)
   val uopFCVT_L_S  = UInt(86, UOPC_SZ)
   val uopFCVT_LU_S = UInt(87, UOPC_SZ)
   val uopFCVT_W_D  = UInt(88, UOPC_SZ)
   val uopFCVT_WU_D = UInt(89, UOPC_SZ)
   val uopFCVT_L_D  = UInt(90, UOPC_SZ)
   val uopFCVT_LU_D = UInt(91, UOPC_SZ)

   val uopFEQ_S     = UInt(92, UOPC_SZ)
   val uopFLT_S     = UInt(93, UOPC_SZ)
   val uopFLE_S     = UInt(94, UOPC_SZ)
   val uopFEQ_D     = UInt(95, UOPC_SZ)
   val uopFLT_D     = UInt(96, UOPC_SZ)
   val uopFLE_D     = UInt(97, UOPC_SZ)

   val uopFCLASS_S  = UInt(98, UOPC_SZ)
   val uopFCLASS_D  = UInt(99, UOPC_SZ)

   val uopFMIN_S    = UInt(100,UOPC_SZ)
   val uopFMAX_S    = UInt(101,UOPC_SZ)
   val uopFMIN_D    = UInt(102,UOPC_SZ)
   val uopFMAX_D    = UInt(103,UOPC_SZ)

   val uopFADD_S    = UInt(104,UOPC_SZ)
   val uopFSUB_S    = UInt(105,UOPC_SZ)
   val uopFMUL_S    = UInt(106,UOPC_SZ)
   val uopFADD_D    = UInt(107,UOPC_SZ)
   val uopFSUB_D    = UInt(108,UOPC_SZ)
   val uopFMUL_D    = UInt(109,UOPC_SZ)

   val uopFMADD_S   = UInt(110,UOPC_SZ)
   val uopFMSUB_S   = UInt(111,UOPC_SZ)
   val uopFNMADD_S  = UInt(112,UOPC_SZ)
   val uopFNMSUB_S  = UInt(113,UOPC_SZ)
   val uopFMADD_D   = UInt(114,UOPC_SZ)
   val uopFMSUB_D   = UInt(115,UOPC_SZ)
   val uopFNMADD_D  = UInt(116,UOPC_SZ)
   val uopFNMSUB_D  = UInt(117,UOPC_SZ)

   val uopFDIV_S    = UInt(118,UOPC_SZ)
   val uopFDIV_D    = UInt(119,UOPC_SZ)
   val uopFSQRT_S   = UInt(120,UOPC_SZ)
   val uopFSQRT_D   = UInt(121,UOPC_SZ)

   val uopSYSTEM    = UInt(122, UOPC_SZ) // pass uop down the CSR pipeline and let it handle it
   val uopSetVl     = UInt(123, UOPC_SZ) // TODO
//   val uopVLD       = UInt(124, UOPC_SZ) // TODO
//   val uopVLD       = UInt(124, UOPC_SZ) // TODO

   // Memory Mask Type Signal
   val MSK_X   = BitPat("b???")
   val MSK_B   = UInt(0, 3)
   val MSK_H   = UInt(1, 3)
   val MSK_W   = UInt(2, 3)
   val MSK_D   = UInt(3, 3)
   val MSK_BU  = UInt(4, 3)
   val MSK_HU  = UInt(5, 3)
   val MSK_WU  = UInt(6, 3)

   // The Bubble Instruction (Machine generated NOP)
   // Insert (XOR x0,x0,x0) which is different from software compiler
   // generated NOPs which are (ADDI x0, x0, 0).
   // Reasoning for this is to let visualizers and stat-trackers differentiate
   // between software NOPs and machine-generated Bubbles in the pipeline.
   val BUBBLE  = UInt(0x4033, 32)


   def NullMicroOp()(implicit p: Parameters): MicroOp =
   {
      val uop = Wire(new MicroOp()(p))
      uop.uopc       := uopNOP // maybe not required, but helps on asserts that try to catch spurious behavior
      uop.bypassable := Bool(false)
      uop.fp_val     := Bool(false)
      uop.is_store   := Bool(false)
      uop.is_load    := Bool(false)
      uop.pdst       := UInt(0)
      uop.dst_rtype  := RT_X
      uop.valid      := Bool(false)
      // TODO these unnecessary? used in regread stage?
      uop.is_br_or_jmp := Bool(false)

      val cs = Wire(new CtrlSignals())
      cs.br_type     := BR_N
      cs.rf_wen      := Bool(false)
      cs.csr_cmd     := rocket.CSR.N
      cs.is_load     := Bool(false)
      cs.is_sta      := Bool(false)
      cs.is_std      := Bool(false)

      uop.ctrl := cs
      uop
   }

}

trait RISCVConstants
{
   // abstract out instruction decode magic numbers
   val RD_MSB  = 11
   val RD_LSB  = 7
   val RS1_MSB = 19
   val RS1_LSB = 15
   val RS2_MSB = 24
   val RS2_LSB = 20
   val RS3_MSB = 31
   val RS3_LSB = 27

   val CSR_ADDR_MSB = 31
   val CSR_ADDR_LSB = 20
   val CSR_ADDR_SZ = 12

   // location of the fifth bit in the shamt (for checking for illegal ops for SRAIW,etc.)
   val SHAMT_5_BIT = 25
   val LONGEST_IMM_SZ = 20
   val X0 = UInt(0)
   val RA = UInt(1) // return address register

   val jal_opc = UInt(0x6f)
   val jalr_opc = UInt(0x67)
   def GetUop(inst: UInt): UInt = inst(6,0)
   def GetRd (inst: UInt): UInt = inst(RD_MSB,RD_LSB)
   def GetRs1(inst: UInt): UInt = inst(RS1_MSB,RS1_LSB)
   def IsCall(inst: UInt): Bool = (inst === rocket.Instructions.JAL ||
                                  inst === rocket.Instructions.JALR) && GetRd(inst) === RA
   def IsReturn(inst: UInt): Bool = GetUop(inst) === jalr_opc && GetRd(inst) === X0 && GetRs1(inst) === RA

   def ComputeBranchTarget(pc: UInt, inst: UInt, xlen: Int, coreInstBytes: Int): UInt =
   {
      val b_imm32 = Cat(Fill(20,inst(31)), inst(7), inst(30,25), inst(11,8), UInt(0,1))
//      (pc + Sext(b_imm32, xlen)) & SInt(-coreInstBytes)
      ((pc + Sext(b_imm32, xlen)).asSInt & SInt(-coreInstBytes)).asUInt
   }
   def ComputeJALTarget(pc: UInt, inst: UInt, xlen: Int, coreInstBytes: Int): UInt =
   {
      val j_imm32 = Cat(Fill(12,inst(31)), inst(19,12), inst(20), inst(30,25), inst(24,21), UInt(0,1))
      ((pc + Sext(j_imm32, xlen)).asSInt & SInt(-coreInstBytes)).asUInt
   }


   def InstsStr(insts: UInt, width: Int) =
   {
      //var string = Str("") //sprintf("") XXX TODO sprintf is missing in chisel3
      //for (w <- 0 until width)
      //{
      //   string = sprintf("%s(DASM(%x))", string, insts(((w+1)*32)-1,w*32))
      //}
      //string
   }


}

trait ExcCauseConstants
{
   // a memory disambigious misspeculation occurred
   val MINI_EXCEPTION_MEM_ORDERING = UInt(13)
   // an instruction needs to be replayed (e.g., I$ asks for a replay)
   val MINI_EXCEPTION_REPLAY = UInt(14)
   require (!rocket.Causes.all.contains(13))
   require (!rocket.Causes.all.contains(14))
}

/* Automatically generated by parse-opcodes */
object RVVInstructions
{
   def VSETCFG            = BitPat("b111100000000?????000000001101011")
   def VSETVL             = BitPat("b111100000001?????000?????1101011")
   def VIDX               = BitPat("b11110000001000000000?????1101011")
   def VADD               = BitPat("b1111100??????????????????1101011")
   def VSLL               = BitPat("b1111101??????????????????1101011")
   def VFADD              = BitPat("b0000000??????????????????1101011")
   def VL                 = BitPat("b?????????????????001?????1100111")
   def VS                 = BitPat("b?????????????????010?????1100111")
   def VLST               = BitPat("b?????????????????011?????1100111")
   def VSST               = BitPat("b?????????????????100?????1100111")
   def VLX                = BitPat("b?????????????????101?????1100111")
   def VSX                = BitPat("b?????????????????110?????1100111")
}

}
