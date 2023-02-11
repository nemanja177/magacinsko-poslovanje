import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArtikalComponent } from './list/artikal.component';
import { ArtikalDetailComponent } from './detail/artikal-detail.component';
import { ArtikalUpdateComponent } from './update/artikal-update.component';
import { ArtikalDeleteDialogComponent } from './delete/artikal-delete-dialog.component';
import { ArtikalRoutingModule } from './route/artikal-routing.module';

@NgModule({
  imports: [SharedModule, ArtikalRoutingModule],
  declarations: [ArtikalComponent, ArtikalDetailComponent, ArtikalUpdateComponent, ArtikalDeleteDialogComponent],
})
export class ArtikalModule {}
