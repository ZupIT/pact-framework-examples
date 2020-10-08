export interface Repository<T> {
    getAll(): Promise<T[]>
    getById(id: string): Promise<T | undefined>
    store(entity: T): Promise<T>
    update(entity: T): Promise<T | undefined>
    delete(id: string): Promise<boolean>
}
  