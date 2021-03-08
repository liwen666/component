          /**
             * Nthreads=CPU数量
             * Ucpu=目标CPU的使用率，0<=Ucpu<=1
             * W/C=任务等待时间与任务计算时间的比率
             */
            Nthreads = Ncpu*Ucpu*(1+W/C)