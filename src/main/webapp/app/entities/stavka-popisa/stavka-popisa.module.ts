import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StavkaPopisaComponent } from './list/stavka-popisa.component';
import { StavkaPopisaDetailComponent } from './detail/stavka-popisa-detail.component';
import { StavkaPopisaUpdateComponent } from './update/stavka-popisa-update.component';
import { StavkaPopisaDeleteDialogComponent } from './delete/stavka-popisa-delete-dialog.component';
import { StavkaPopisaRoutingModule } from './route/stavka-popisa-routing.module';

@NgModule({
  imports: [SharedModule, StavkaPopisaRoutingModule],
  declarations: [StavkaPopisaComponent, StavkaPopisaDetailComponent, StavkaPopisaUpdateComponent, StavkaPopisaDeleteDialogComponent],
})
export class StavkaPopisaModule {}
