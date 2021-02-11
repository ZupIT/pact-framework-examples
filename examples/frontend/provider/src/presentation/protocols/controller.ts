import { Request, Response } from "express";

export interface Controller {
  getAll(req: Request, res: Response): Promise<any>
  getById(req: Request, res: Response): Promise<any>
  save(req: Request, res: Response): Promise<any>
  update(req: Request, res: Response): Promise<any>
  deleteById(req: Request, res: Response): Promise<any>
}
